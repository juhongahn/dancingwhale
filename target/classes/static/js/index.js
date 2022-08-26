const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;

$(document).ready(function (){
    $('.visual').slick();

    $('.text-box').each(function (){

        var content = $(this).children('.box-contents');
        var content_txt = content.text();
        var content_txt_short = content_txt.substring(0,100)+"...";
        var btn_more = $('<a href="javascript:void(0)" class="more text-muted"' +
            'style="text-decoration: none; font-size: 14px;">더 보기</a>');

        $(this).append(btn_more);

        if(content_txt.length >= 100){
            content.html(content_txt_short);

        }else{
            btn_more.hide();
        }
        btn_more.click(toggle_content);

        function toggle_content(){
            if($(this).hasClass('short')){
                // 접기 상태
                $(this).html('더 보기');
                content.html(content_txt_short)
                $(this).removeClass('short');
            }else{
                // 더보기 상태
                $(this).html('접기');
                content.html(content_txt);
                $(this).addClass('short');

            }
        }
    })
});

function indexPage(page){
    location.href="/?page=" + page;
}

function mypage(page) {
    location.href="/post/my-posts?page=" + page;
}

function checkDescriptionCnt(description) {
    if (description.length > 300) {
        return true;
    } else {
        return false;
    }
}

function checkBlank(description) {

    //공백이면 알림창 띄우고 종료
    if (description.trim() === '') {
        return true;
    } else {
        return false;
    }
}

function formEmptyCheck(_this){

    let description = $('#message-text').val();

    if (_this.textContent === "수정") {
        
        // 게시물 수정
        // 기존에 있던 img와 새로 추가된 이미지를 서버에 보내야한다.
        let onlyMe = $("#onlyMe").is(":checked");
        let postId = $('#postId').val();
        let memberId = $('#memberId').val();

        if (checkDescriptionCnt(description)) {
            alert("300자 이상 넘길 수 없어요.");
            return;
        }

        if (checkBlank(description)) {
            alert("최소 한 글자 이상 입력해 주세요.");
            return;
        }

        var postImgIdLength = $("input[name=postImgId]").length;
        var postImgIds = new Array(postImgIdLength);

        for(var i=0; i<postImgIdLength; i++){
            postImgIds[i] = $("input[name=postImgId]").eq(i).val();
        }

        // 파일 업로드
        var formData = new FormData();

        // 데이터 담아주고
        var data = {
            "id"            : postId,
            "onlyMe"            : onlyMe,
            "description"       : description,
            "postImgIds"        : postImgIds,
            "memberId"          : memberId
        }

        // 인풋파일은 없을수도 있다.
        const  fileInput = document.querySelector("#formFileMultiple");
        const  files = fileInput.files;
        const fileListLength = files.length;

        for (let i = 0; i < fileListLength; i++) {
            formData.append("files", files.item(i));
        }
        formData.append('key', new Blob([ JSON.stringify(data)], {type : "application/json"}));

        // ajax 처리 부분 *
        $.ajax({
            url: "/post/" + postId + "/edit",
            data: formData,
            type : 'POST',
            datatype: "json",
            beforeSend : function(xhr)
            { xhr.setRequestHeader(header, token); },
            contentType: false,
            processData: false,
            enctype : 'multipart/form-data',
            success: function(resData) {
                alert("수정됐습니다.");
                location.reload();
            }
        });

    } else {
        // 새 글쓰기
        if (checkDescriptionCnt(description)) {
            alert("300자 이상 넘길 수 없어요.");
            return;
        }

        if (checkBlank(description)) {
            alert("최소 한 글자 이상 입력해 주세요.");
            return;
        }
        $('#modal-form').submit();
    }



}

function requestPostDelete(postId, memberId) {


    if (confirm("삭제하시겠습니까?")) {
        fetch('/post/delete', {
            method: 'post',
            headers: {
                'header': header,
                'X-Requested-With': 'XMLHttpRequest',
                'X-CSRF-Token': token,
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'postId': postId,
                'memberId': memberId
            })
        })
            .then(res => res.json())
            .then(data => {
                alert(data);
                window.location.reload();
            })
    }
}

function openEditForm(postId, memberId) {

    var exampleModal = document.getElementById('exampleModal');
    var modal = bootstrap.Modal.getOrCreateInstance(exampleModal);

    fetch('/post/'+ postId +'/edit')
        .then((res) => res.json())
        .then((data) => {
                $('#message-text').val(data.description);
                $('#memberId').val(data.memberId);
                $("input:checkbox[id='onlyMe']").prop("checked", data.onlyMe);
                $('#postId').val(postId);


                data.postImgDtoList.forEach(function (postImgDto) {
                    // div에 이미지추가.
                    var str = '<li class="ui-state-default">';
                    str += '<img src="'+postImgDto.imgUrl +'" alt="'+ postImgDto.imgName +'" width=80 height=80>';
                    str += '<span class="delBtn" onclick="delImg(this)">x</span>';
                    str += '<input type="hidden" class="postImgId" name="postImgId" value = ' + postImgDto.id + '>';
                    str += '</li>';
                    $(str).appendTo('#imgPreview');
                });
            }
        );
    var editBtn  = document.getElementById('editFormOpenBtn');
    modal.show(editBtn);
}