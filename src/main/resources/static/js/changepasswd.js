function validateChangePasswordForm() {
    const currentPassword = document.getElementById("currentPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    const errorMessages = document.getElementById("error-messages-client");
    const errorList = document.getElementById("error-list");
    errorList.innerHTML = "";
    errorMessages.style.display = "none";

    let hasError = false;

    // Check if any field is empty
    if (!currentPassword) {
        const errorItem = document.createElement("li");
        errorItem.textContent = "Current password is required.";
        errorList.appendChild(errorItem);
        hasError = true;
    }
    if (!newPassword) {
        const errorItem = document.createElement("li");
        errorItem.textContent = "New password is required.";
        errorList.appendChild(errorItem);
        hasError = true;
    }
    if (!confirmPassword) {
        const errorItem = document.createElement("li");
        errorItem.textContent = "Confirm password is required.";
        errorList.appendChild(errorItem);
        hasError = true;
    }

    if (newPassword && confirmPassword && newPassword != confirmPassword) {
        const errorItem = document.createElement("li");
        errorItem.textContent = "New password and confirm password do not match.";

        errorList.appendChild(errorItem);

        hasError=true;
    }
    if (hasError) {
        errorMessages.style.display = "block";
        // Stop the form from submitting
        return false;
    }

    // Allow form submission if there are no errors
    return true;
}