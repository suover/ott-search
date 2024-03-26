function checkDuplicate(type, value, currentValue, callback) {
    // 현재 값과 새 값이 같으면 중복 검사를 수행하지 않음
    if (value === currentValue) {
        callback(false);
        return;
    }

    var url = type === 'nickname' ? '/user/check-nick' : '/user/check-mobile';
    var data = {};
    data[type] = value;
    
    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        success: function(response) {
            var isAvailable = type === 'nickname' ? response.isNickAvailable : response.isNumAvailable;
            callback(!isAvailable); // 중복이면 true, 아니면 false 반환
        },
        error: function() {
            alert("중복 확인 중 오류가 발생했습니다.");
            callback(false);
        }
    });
}

// 수정 버튼 클릭 이벤트
$('#mod-btn').click(function(e) {
    e.preventDefault();
    const userId = $('#userId').val();
    const currentNickname = $('#currentNickname').val();
    const currentPhoneNumber = $('#currentPhoneNumber').val();
    const newNickname = $('#nickname').val();
    const newPhoneNumber = $('#phone_number').val();
    const password = $('#password').val();

    checkDuplicate('nickname', newNickname, currentNickname, function(isNicknameDuplicate) {
        if (isNicknameDuplicate) {
            alert("닉네임이 이미 사용 중입니다.");
        } else {
            checkDuplicate('phoneNumber', newPhoneNumber, currentPhoneNumber, function(isPhoneNumberDuplicate) {
                if (isPhoneNumberDuplicate) {
                    alert("전화번호가 이미 사용 중입니다.");
                } else {
                    updateUser(userId, newNickname, newPhoneNumber, password);
                }
            });
        }
    });
});

function updateUser(userId, nickname, phoneNumber, password) {
    let userInfo = {
        userId: userId,
        nickname: nickname,
        phoneNumber: phoneNumber,
        password: password
    };

    $.ajax({
        method: 'POST',
        url: '/user/update',
        contentType: 'application/json',
        data: JSON.stringify(userInfo),
        success: function(response) {
            // 수정 성공 메시지 표시
            alert('정보가 성공적으로 수정되었습니다.');

            // 페이지의 닉네임 표시 부분을 새로운 닉네임으로 업데이트
            $('#nickname-display').text(nickname + "님, 안녕하세요!");

            // 현재 닉네임 값 업데이트 (중복 검사 로직을 위해)
            $('#currentNickname').val(nickname);
        },
        error: function(xhr, status, error) {
            var errorMsg = xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : '정보 수정 중 오류가 발생했습니다.';
            alert(errorMsg);
        }
    });
}

// 회원탈퇴 확인 함수
function confirmDelete() {
    if (confirm("정말로 탈퇴하시겠습니까?")) {
        deleteUser();
    }
}

// 회원탈퇴 처리 함수
function deleteUser() {
    const userId = $('#userId').val();

    $.ajax({
        url: '/user/delete',
        type: 'POST',
        data: { userId: userId },
        success: function(response) {
            alert('회원 탈퇴 처리가 완료되었습니다.');
            window.location.href = '/'; // 탈퇴 후 메인 페이지로 리디렉션
        },
        error: function() {
            alert('회원 탈퇴 처리 중 오류가 발생했습니다.');
        }
    });
}