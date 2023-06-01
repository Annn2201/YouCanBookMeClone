function validateUpdatePassword() {
    var newPasswordElement = $("#newPassword");
    var confirmNewPasswordElement = $("#confirmNewPassword");

    var status = true;
    var pattern = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-+_!@#$%^&*.,?]).+$");
    if (newPasswordElement.val().length < 6) {
        alert('Your password must have 6 characters or more!');
        newPasswordElement.css("border-color", "rgb(255,0,0)");
        status = false;
    } else if (!pattern.test(newPasswordElement.val())) {
        alert("Your password must contain uppercase, lowercase, special character and number");
        newPasswordElement.css("border-color", "rgb(255,0,0)");
        status = false;
    } else if (newPasswordElement.val() !== confirmNewPasswordElement.val()) {
        alert("Your confirm password doesn't match with password");
        confirmNewPasswordElement.css("border-color", "rgb(255,0,0)");
        status = false;
    }
    if (status === true) {
        $("#form").submit();
    }
}