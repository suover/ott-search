function updateAllRoles() {
    var updatePromises = [];

    $('.admin-select').each(function() {
        var userId = $(this).data('user-id');
        var role = $(this).val();

        // 각 요청에 대한 Promise를 배열에 저장
        updatePromises.push(sendRoleUpdateRequest(userId, role));
    });

    // 모든 Promise가 완료된 후 처리
    Promise.all(updatePromises).then(function(results) {
        var allSuccess = results.every(function(res) { return res.isUpdate; });
        if (allSuccess) {
            alert('모든 권한 변경이 성공적으로 완료되었습니다.');
        } else {
            alert('하나 이상의 권한 변경에 실패했습니다.');
        }
    }).catch(function() {
        alert('권한 변경 중 오류가 발생했습니다.');
    });
}

function sendRoleUpdateRequest(userId, role) {
    return new Promise(function(resolve, reject) {
        $.ajax({
            url: '/updateRole/' + userId,
            type: 'POST',
            data: { userId: userId, role: role },
            success: function(res) {
                resolve(res);
            },
            error: function() {
                reject(new Error('권한 변경 중 오류 발생: 사용자 ID ' + userId));
            }
        });
    });
}