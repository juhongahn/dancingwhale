function spinnerShow() {
    const signUpBtnText = document.getElementById('signUpBtnText');
    const signUpBtn = document.getElementById('signUpBtn');
    const spinner = document.getElementById('spinner');
    const signUpForm = document.getElementById('signUpForm');

    signUpBtnText.classList.add('visually-hidden');
    signUpBtn.disabled = true;
    spinner.classList.replace('visually-hidden', 'spinner-border');
    signUpForm.submit();
}