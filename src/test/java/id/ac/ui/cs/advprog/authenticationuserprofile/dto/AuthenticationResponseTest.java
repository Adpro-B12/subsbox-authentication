package id.ac.ui.cs.advprog.authenticationuserprofile.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class AuthenticationResponseTest {

    @Test
    void testAuthenticationResponseGettersAndSetters() {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setStatus("success");
        response.setMessage("message");
        Map<String, String> data = new HashMap<>();
        data.put("key", "value");
        response.setData(data);

        assertEquals("success", response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals(data, response.getData());
    }

    @Test
    void testAuthenticationResponseBuilder() {
        Map<String, String> data = new HashMap<>();
        data.put("key", "value");

        AuthenticationResponse response = AuthenticationResponse.builder()
                .status("success")
                .message("message")
                .data(data)
                .build();

        assertEquals("success", response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals(data, response.getData());
    }

    @Test
    void testAuthenticationResponseConstructor() {
        Map<String, String> data = new HashMap<>();
        data.put("key", "value");

        AuthenticationResponse response = new AuthenticationResponse(
                "success", "message", data
        );

        assertEquals("success", response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals(data, response.getData());
    }

    @Test
    void testEqualsAndHashCode() {
        Map<String, String> data1 = new HashMap<>();
        data1.put("key", "value");
        AuthenticationResponse response1 = new AuthenticationResponse(
                "success", "message", data1
        );

        Map<String, String> data2 = new HashMap<>();
        data2.put("key", "value");
        AuthenticationResponse response2 = new AuthenticationResponse(
                "success", "message", data2
        );

        Map<String, String> data3 = new HashMap<>();
        data3.put("differentKey", "differentValue");
        AuthenticationResponse response3 = new AuthenticationResponse(
                "failed", "differentMessage", data3
        );

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }

    @Test
    void testToString() {
        Map<String, String> data = new HashMap<>();
        data.put("key", "value");

        AuthenticationResponse response = new AuthenticationResponse(
                "success", "message", data
        );
        String expectedToString = "AuthenticationResponse(status=success, message=message, data={key=value})";

        assertEquals(expectedToString, response.toString());
    }

    @Test
    void testGetToken() {
        Map<String, String> data = new HashMap<>();
        data.put("token", "testToken");

        AuthenticationResponse response = AuthenticationResponse.builder()
                .data(data)
                .build();

        assertEquals("testToken", response.getToken());
    }

    @Test
    void testSetStatus() {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setStatus("newStatus");
        assertEquals("newStatus", response.getStatus());
    }

    @Test
    void testSetMessage() {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setMessage("newMessage");
        assertEquals("newMessage", response.getMessage());
    }

    @Test
    void testSetData() {
        AuthenticationResponse response = new AuthenticationResponse();
        Map<String, String> newData = new HashMap<>();
        newData.put("newKey", "newValue");
        response.setData(newData);
        assertEquals(newData, response.getData());
    }

    @Test
    void testGetStatus() {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setStatus("newStatus");
        assertEquals("newStatus", response.getStatus());
    }

    @Test
    void testGetMessage() {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setMessage("newMessage");
        assertEquals("newMessage", response.getMessage());
    }

    @Test
    void testGetData() {
        AuthenticationResponse response = new AuthenticationResponse();
        Map<String, String> newData = new HashMap<>();
        newData.put("newKey", "newValue");
        response.setData(newData);
        assertEquals(newData, response.getData());
    }
}
