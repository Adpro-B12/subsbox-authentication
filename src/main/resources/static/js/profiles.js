document.addEventListener('DOMContentLoaded', async () => {
    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = '/login';
        return;
    }

    const response = await fetch('/auth/profiles', {
        method: 'GET',
        headers: { 'Authorization': `Bearer ${token}` }
    });

    if (response.ok) {
        const profiles = await response.json();
        const profilesContainer = document.getElementById('profiles');
        profiles.forEach(profile => {
            const profileDiv = document.createElement('div');
            profileDiv.classList.add('profile');
            profileDiv.innerHTML = `
                <p>Username: ${profile.username}</p>
                <p>Full Name: ${profile.fullName}</p>
                <p>Email: ${profile.email}</p>
                <p>Role: ${profile.role}</p>
                <p>Phone Number: ${profile.phoneNumber}</p>
                <p>Address: ${profile.address}</p>
            `;
            profilesContainer.appendChild(profileDiv);
        });
    } else {
        alert('Failed to fetch profiles');
    }
});
