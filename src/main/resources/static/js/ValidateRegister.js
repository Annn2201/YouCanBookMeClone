function validateRegister() {
    var passwordElement = $("#password");
    var confirmPasswordElement = $("#confirmPassword");

    var status = true;
    var pattern = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-+_!@#$%^&*.,?]).+$");
    if (passwordElement.val().length < 6) {
        alert('Your password must have 6 characters or more!');
        passwordElement.css("border-color", "rgb(255,0,0)");
        status = false;
    } else if (!pattern.test(passwordElement.val())) {
        alert("Your password must contain uppercase, lowercase, special character and number");
        passwordElement.css("border-color", "rgb(255,0,0)");
        status = false;
    } else if (passwordElement.val() !== confirmPasswordElement.val()) {
        alert("Your confirm password doesn't match with password");
        confirmPasswordElement.css("border-color", "rgb(255,0,0)");
        status = false;
    }
    if (status === true) {
        $("#form").submit();
    }

}


