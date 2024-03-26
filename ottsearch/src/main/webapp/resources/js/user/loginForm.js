const loginForm = document.querySelector('#login-form');
const emailInput = document.querySelector('#email');
const pwdInput = document.querySelector('#pw');
const submitBtn = document.querySelector('#submit-btn');

// 이메일 유효성 정규식
const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;

let emailIsValid = false;

// 이메일 유효성 검사, 이메일 형식이 아니거나 값이 없을 때 문구 출력
const validateEmail = () => {
  let emailValue = emailInput.value;
  let confirmTxt = emailInput.parentElement.querySelector('p');

  if (emailValue.trim().length === 0) {
    confirmTxt.classList.remove('hide');
    return;
  };

  if (emailPattern.test(emailValue)) {
    emailIsValid = true;
  } else {
    confirmTxt.classList.remove('hide');
    confirmTxt.innerText = '유효하지 않은 이메일입니다.';
  };
};

// 비밀번호 확인은 백엔드에서 추가적으로
const validatePassword = () => {
  let pwdValue = pwdInput.value;

  if (pwdValue.trim().length === 0) {
    let confirmTxt = pwdInput.parentElement.querySelector('p');
    confirmTxt.classList.remove('hide');
  }
};

submitBtn.addEventListener('click', () => {
  validateEmail();
  validatePassword();

  if (emailIsValid) {
    loginForm.submit();
  }
});

// 사용자가 입력할 때 문구제거
const userTyping = (inputType) => {
  let confirmTxt = inputType.parentElement.querySelector('p');

  inputType.addEventListener('focus', () => {
    confirmTxt.classList.add('hide');
  });
};

userTyping(emailInput);
userTyping(pwdInput);