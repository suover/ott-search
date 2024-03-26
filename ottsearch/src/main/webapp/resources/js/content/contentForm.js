// 제목 중복 검사
const checkTitle = (contentId) => {
  $("#title").blur(function(){
    var title = $(this).val();
    var data = {title: title };

    if (contentId !== '') {
        data["contentId"] = contentId; 
    }

    $.ajax({
        url: "/content/checkTitle",
        type: "GET",
        data: data,
        success: function(response) {
            if(response.exists) {
              $("#title-message").css('visibility', 'visible');
              $("#register-btn").prop('disabled', true);
            } else {
      	      $("#title-message").css('visibility', 'hidden');
              $("#register-btn").prop('disabled', false);
            }
        }
    });
	});
};

// 수정페이지 이미지 있을 때
const viewImage = (src) => {
  const container = document.querySelector('.image_container');
  let img = document.getElementById('thumbnail-image');
  
  container.style.display = "block";
  img.src = src;
}

const setThumbnail = (event) => {
  let input = event.target;
  let reader = new FileReader();

  reader.onload = function () {
      let container = document.querySelector('.image_container');
      container.style.display = "block";
      let img = document.getElementById('thumbnail-image');
      img.src = reader.result;
  };

  reader.readAsDataURL(input.files[0]);
}

const validateForm = () => {
  const checkboxes = document.querySelectorAll('input[type="checkbox"]');
  const radios = document.querySelectorAll('input[type="radio"]');
  const actorInputs = document.querySelectorAll('.actor-input');
  const formInputs = document.querySelectorAll('.form-control');

  const checkboxChecked = Array.from(checkboxes).some((checkbox) => checkbox.checked);
  const radioChecked = Array.from(radios).some((radio) => radio.checked);
  const actorInputFilled = Array.from(actorInputs).some((actorInput) => actorInput.value.trim() !== '');
  const allInputsFilled = Array.from(formInputs).every((input) => input.value.trim() !== '');

  if (!checkboxChecked) {
      alert('모든 폼을 작성하세요');
  } else if (!radioChecked || !actorInputFilled || !allInputsFilled) {
      let errorMessage = "모든 폼을 작성하세요. ";

      if (!radioChecked) {
          errorMessage += "플랫폼, ";
      }

      if (!actorInputFilled) {
          errorMessage += "배우, ";
      }

      if (!allInputsFilled) {
          errorMessage += "다른 필수 정보, ";
      }

      errorMessage = errorMessage.slice(0, -2); // 마지막에 추가된 쉼표와 공백 제거
      alert(errorMessage + "를(을) 작성하세요");
  } else {
    document.querySelector('#main-content form').submit();
  }
}

// 버튼 누르면 배우 추가
const addActorBtn = document.querySelector('#add-btn');
addActorBtn.addEventListener('click', () => {
  const actorInput = document.querySelector('#actor');
  let actorValue = actorInput.value;

  if (actorValue.trim().length !== 0) {
    handleAddActor(actorValue);
  }

  actorInput.value = '';
});
// 엔터키 눌렀을 때 추가
const actorInput = document.querySelector('#actor');
actorInput.addEventListener('keydown', (e) => {
  if (e.keyCode === 13) {
    let value = actorInput.value;
    handleAddActor(value);
    actorInput.value = '';
  }
});

// 배우가 추가될 때 element 생성 
const handleAddActor = (actorValue) => {
  const inputWrap = document.querySelector(".actor-list");
  const newInputWrap = document.createElement('div');
  const newInputEl = document.createElement('input');
  const delBtn = document.createElement('button');
  const testStr = /[a-zA-Z]/;
  let inputLen = actorValue.length;

  newInputEl.type = 'text';
  newInputEl.name = 'actorNames';
  newInputEl.className = 'actor-input';
  if (testStr.test(actorValue)) {
    newInputEl.style.width = `${inputLen * 8}px`;
  } else {
    newInputEl.style.width = `${inputLen * 16}px`;
  }
  newInputEl.value = actorValue;

  delBtn.id = 'delete-btn';
  delBtn.type = "button";
  delBtn.innerHTML = `
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
      <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
      <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
    </svg>
  `;

  newInputWrap.append(newInputEl);
  newInputWrap.append(delBtn);

  inputWrap.append(newInputWrap);
  // 삭제 버튼을 누르면 해당 버튼이 속한 div가 삭제
  delBtn.addEventListener('click', (e) => {
    let removeTarget = e.target.parentElement;

    removeTarget.remove();
  })
}

const submitComment = () => {
  // 입력된 댓글 가져오기
  var comment = document.getElementById('commentInput').value;

  // 여기에서 서버에 댓글을 전송하는 로직을 구현할 수 있습니다.
  // 예시: 여기서는 간단히 콘솔에 출력합니다.
  alert('작성된 댓글:', comment);

  // 댓글 입력 후 입력창 초기화
  document.getElementById('commentInput').value = '';
}