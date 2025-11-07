
function showAlert(message, type = "danger") {
    const alertContainer = document.getElementById("alert-container");
    alertContainer.innerHTML = `
        <div class="alert ${type}">
            ${message}
        </div>
    `;

    // Auto-hide after 4 seconds
    setTimeout(() => {
        const alert = alertContainer.querySelector(".alert");
        if (alert) {
            alert.style.transition = "opacity 0.5s";
            alert.style.opacity = "0";
            setTimeout(() => alert.remove(), 500);
        }
    }, 4000);
}

document
    .getElementById("login-signup-form")
    .addEventListener("submit", async function (event) {
        event.preventDefault();

        const form = event.target;
        const firstName = form.firstName.value;
        const lastName = form.lastName.value;
        const email = form.email.value;
        const userName = form.userName.value;
        const password = form.password.value;
        const confirmPassword = form.confirmPassword.value;

        if (password !== confirmPassword) {
            showAlert("Passwords do not match!", "danger");
            return;
        }

        try {
            const response = await fetch("/public", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    firstName: firstName,
                    lastName: lastName,
                    userName: userName,
                    email: email,
                    password: password,
                }),
            });

            if (response.status === 201) {
                alert("Account created successfully! Redirecting...", "success");
                setTimeout(() => {
                    window.location.href = "/login";
                }, 2000);
            } else if (response.status === 409) {
                // Conflict - username already exists
                const message = await response.text();
                showAlert(message || "Username already exists. Please choose another.", "danger");
            } else {
                const message = await response.text();
                alert(message || "Failed to create account.", "danger");
            }
        } catch (error) {
            alert("Error connecting to server. Please try again.");
        }
    });
