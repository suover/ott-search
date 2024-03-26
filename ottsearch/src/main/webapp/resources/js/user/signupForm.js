const signupForm = document.querySelector('form');
const submitBtn = document.querySelector('#submit-btn');
const resetBtn = document.querySelector('#reset-btn');
const emailDuplBtn = document.querySelector('#email-dupl');
const nickDuplBtn = document.querySelector('#nick-dupl');
const phoneDuplBtn = document.querySelector('#phone-dupl');

const birthday = document.querySelector('#birthday');
const birthText = document.querySelector('.birth-ex');
let birthIsValid = false;

// 이메일 관련
const emailInput = {
  inputType: document.querySelector('#email'),
  textEl: document.querySelector('.email-ex'),
  noValueText: '이메일을 입력해주세요.',
  invalidText: '유효하지 않은 이메일입니다.',
  pattern: /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/,
  isValid: false
}
// 중복확인 밖으로 빼냄..
let emailIsDupl = true;
let nicknameIsDupl = true;
let phoneIsDupl = true;

// 비밀번호 관련
const passwordInput = {
  inputType: document.querySelector('#password'),
  textEl: document.querySelector('.pw-ex'),
  noValueText: '비밀번호를 입력해주세요.',
  invalidText: '유효하지 않은 비밀번호입니다.',
  pattern: /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&#.~_-])[A-Za-z\d@$!%*?&#.~_-]{8,20}$/,
  isValid: false
}

// 전화번호 관련
const phoneNumberInput = {
  inputType: document.querySelector('#phone'),
  textEl: document.querySelector('.tel-ex'),
  noValueText: '전화번호를 입력해주세요.',
  invalidText: '000-0000-0000 형식으로 입력해주세요.',
  pattern: /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/,
  isValid: false
}

// nickname 관련
const nicknameInput = document.querySelector('#nickname');
const nickText = document.querySelector('.nickname-ex');
let nicknameIsValid = false;

const userNameInput = {
  inputType: document.querySelector('#username'),
  textEl: document.querySelector('.name-ex'),
  noValueText: '이름을 입력해주세요.',
  invalidText: '유효하지 않은 이름입니다.',
  pattern: /^[가-힣]+$/,
  isValid: false
}

// 원래대로 돌아가기 위한 텍스트
const EMAIL_EX = '이메일 예시 : abcde123@gmail.com';
const PWD_EX = '영문, 숫자, 특수문자(@, $, !, %, *, #, ?) 포함 8~20자';
const NICK_EX = '2~12자 이내로 입력해주세요.';
const NAME_EX = '한글 이름을 입력해주세요. (영문포함 X)';
const PHONE_EX = '000-0000-0000 형식으로 입력해주세요.';

const warningText = (textEl, text) => {
  textEl.style.color = 'crimson'; // 수정필요
  textEl.innerText = text;
};
const resetText = (textEl, text) => {
  textEl.style.color = '#8b8b8b';
  textEl.innerText = text;
}

// 이메일 중복검사
emailDuplBtn.addEventListener('click', () => {
  let emailValue = emailInput.inputType.value;
  let checkData = { email: emailValue };
  
  if (emailValue.trim().length === 0) {
    warningText(emailInput.textEl, noValueText);
    return;
  }
  
  if (!(emailInput.pattern.test(emailValue))) {
    warningText(emailInput.textEl, emailInput.invalidText);
    return;
  }

  $.ajax({
    method: 'POST',
    url: 'user/check-email',
    data: checkData,
    success: function(response) {

      if (response.isEmailAvailable) {
        emailInput.textEl.style.color = '#8b8b8b';
        emailInput.textEl.innerText = '사용 가능한 이메일입니다.';
        emailIsDupl = false;
      } else {
        emailInput.textEl.style.color = 'crimson';
        emailInput.textEl.innerText = '중복된 이메일입니다.';
      }
    },
    error: function(xhr, status, error) {
      console.error("Error occurred: " + error);
    }
  });
  emailInput.isValid = true;
});

// 닉네임 중복검사
nickDuplBtn.addEventListener('click', () => {
  let nicknameValue = nicknameInput.value;
  let checkData = { nickname: nicknameValue };

  if (nicknameValue.trim().length < 2) { // 2글자 이하
    text = '2글자 이상의 닉네임을 입력해주세요.';
    warningText(nickText, text) ;
    return;
  } else if (nicknameValue.trim().length > 12) { // 12글자 이상
    text = '12글자 이하의 닉네임을 입력해주세요.';
    warningText(nickText, text) ;
    return;
  };

  $.ajax({
    method: 'POST',
    url: 'user/check-nick',
    data: checkData,
    success: function(response) {

      if (response.isNickAvailable) {
        nickText.style.color = '#8b8b8b';
        nickText.innerText = '사용 가능한 닉네임입니다.';
        nicknameIsDupl = false;
      } else {
        nickText.style.color = 'crimson';
        nickText.innerText = '중복된 닉네임입니다.';
      }
    },
    error: function(xhr, status, error) {
      console.error("Error occurred: " + error);
    }
  });
  nicknameIsValid = true;
});

// 폰 번호 중복검사
phoneDuplBtn.addEventListener('click', () => {
  let phoneValue = phoneNumberInput.inputType.value;
  let checkData = { phoneNumber: phoneValue };
  
  if (phoneValue.trim().length === 0) {
    warningText(phoneNumberInput.textEl, noValueText);
    return;
  }
  
  if (!(phoneNumberInput.pattern.test(phoneValue))) {
    warningText(phoneNumberInput.textEl, phoneNumberInput.invalidText);
    return;
  }

  $.ajax({
    method: 'POST',
    url: 'user/check-mobile',
    data: checkData,
    success: function(response) {

      if (response.isNumAvailable) {
        phoneNumberInput.textEl.style.color = '#8b8b8b';
        phoneNumberInput.textEl.innerText = '사용 가능한 번호입니다.';
        phoneIsDupl = false;
      } else {
        phoneNumberInput.textEl.style.color = 'crimson';
        phoneNumberInput.textEl.innerText = '중복된 번호입니다.';
      }
    },
    error: function(xhr, status, error) {
      console.error("Error occurred: " + error);
    }
  });
  phoneNumberInput.isValid = true;
});

// 이메일, 패스워드, 전화번호 유효성 검사 - 패스워드 때문에 일단 살려둠
const validateInput = (inputObject) => {
  let {inputType, textEl, noValueText, invalidText, pattern, isValid} = inputObject;
  let inputValue = inputType.value;

  if (inputValue.trim().length === 0) {
    warningText(textEl, noValueText);
    inputObject.isValid = false;
    return;
  }

  if (inputObject === emailInput) {
    console.log('holding');
    return;
  }

  if (pattern.test(inputValue)) {
    inputObject.isValid = true;
  } else {
    warningText(textEl, invalidText);
    inputObject.isValid = false;
  }
}

const validateNickname = () => {
  let nicknameValue = nicknameInput.value.trim();
  let text;

  if (nicknameValue.trim().length < 2) { // 2글자 이하
    text = '2글자 이상의 닉네임을 입력해주세요.';
    warningText(nickText, text) ;
  } else if (nicknameValue.trim().length > 12) { // 12글자 이상
    text = '12글자 이하의 닉네임을 입력해주세요.';
    warningText(nickText, text) ;
  } else {
    nicknameIsValid = true;
  };
};

// 유저 입력시 경고문구 제거
const userTyping = (inputType, textEl, text) => {
  inputType.addEventListener('input', () => {
    resetText(textEl, text);
  });
};

window.onload = function() {
  userTyping(emailInput.inputType, emailInput.textEl, EMAIL_EX);
  userTyping(passwordInput.inputType, passwordInput.textEl, PWD_EX);
  userTyping(nicknameInput, nickText, NICK_EX);
  userTyping(phoneNumberInput.inputType, phoneNumberInput.textEl, PHONE_EX);
  userTyping(userNameInput.inputType, userNameInput.textEl, NAME_EX);
  
  birthday.addEventListener('change', () => {
    birthText.innerText = '';
  });
}

// 가입버튼 클릭 시
submitBtn.addEventListener('click', () => {
  validateInput(emailInput)
  validateInput(passwordInput);
  validateInput(userNameInput);
  validateInput(phoneNumberInput);
  validateNickname();

  let emailIsValid = emailInput.isValid;
  let pwdIsValid = passwordInput.isValid;
  let nameIsValid = userNameInput.isValid;
  let phoneIsValid = phoneNumberInput.isValid;

  let emailValue = emailInput.inputType.value;
  if (emailValue.trim().length !== 0 && emailIsDupl) {
    let text = '이메일 중복여부를 확인해주세요!';
    warningText(emailInput.textEl, text);
  }
  let nickValue = nicknameInput.value;
  if (nickValue.trim().length !== 0 && nicknameIsDupl) {
    let text = '닉네임 중복여부를 확인해주세요!';
    warningText(nickText, text);
  }
  let phoneValue = phoneNumberInput.inputType.value;
  if (phoneValue.trim().length !== 0 && phoneIsDupl) {
    let text = '휴대전화 번호의 중복여부를 확인해주세요!';
    warningText(phoneNumberInput.textEl, text);
  }

  if (birthday.value === "") {
    warningText(birthText, "생일을 입력해주세요.");
  } else {
    birthIsValid = true;
  }

  let allValueIsValid = emailIsValid && pwdIsValid && nameIsValid && nicknameIsValid && phoneIsValid && birthIsValid && !emailIsDupl && !nicknameIsDupl && !phoneIsDupl;

  if (allValueIsValid) {
    signupForm.submit();
  };
});

// 취소버튼 클릭시
resetBtn.addEventListener('click', () => {
  resetText(emailInput.textEl, EMAIL_EX);
  resetText(passwordInput.textEl, PWD_EX);
  resetText(nickText, NICK_EX);
  resetText(phoneNumberInput.textEl, PHONE_EX);
	resetText(userNameInput.textEl, NAME_EX);
	resetText(birthText, '');
});