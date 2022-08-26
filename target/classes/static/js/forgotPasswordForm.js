function spinnerShow() {
    const forgotPasswordFormBtnText = document.getElementById('forgotPasswordFormText');
    const forgotPasswordFormBtn = document.getElementById('forgotPasswordFormBtn');
    const spinner = document.getElementById('spinner');
    const forgotPasswordForm = document.getElementById('forgotPasswordForm');

    forgotPasswordFormBtnText.classList.add('visually-hidden');
    forgotPasswordFormBtn.disabled = true;
    spinner.classList.replace('visually-hidden', 'spinner-border');
    forgotPasswordForm.submit();
}