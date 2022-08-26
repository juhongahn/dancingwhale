function checkInputLength(_this) {
    if (_this.value.length < 8) {
        document.getElementById('password-error').innerHTML = "8자 이상 입력해 주세요.";
    } else {
        document.getElementById('password-error').innerHTML = "";
    }
}

function checkPasswordMatch(fieldConfirmPassword) {
    let password = document.getElementById('password').value;
    if (fieldConfirmPassword.value !== password) {
        document.getElementById('confirm-password-error').innerHTML = "비밀번호가 일치하지 않습니다.";
    } else {
        document.getElementById('confirm-password-error').innerHTML = "";
    }
}

function checkValidation() {
    if (document.getElementById('password').value !== document.getElementById('confirm-password').value) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
    } else {
        document.getElementById('reset-form').submit();
    }
}