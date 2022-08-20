$(window).on("load", function () {
    <!-- 모달 js -->
    var exampleModal = document.getElementById('exampleModal');
    var modal = bootstrap.Modal.getOrCreateInstance(exampleModal);

    <!--모달 오픈 이벤트 등록 -->
    exampleModal.addEventListener('show.bs.modal', function (event) {

        var button = event.relatedTarget;
        var recipient = button.getAttribute('data-bs-whatever');
        var confirmBtn = document.getElementById('postSubmitBtn');
        var modalTitle = document.getElementById('exampleModalLabel');

        if (recipient === "new") {
            // 비워주고.
            $('#message-text').val('');
            $('#imgPreview').empty();
            $('#formFileMultiple').val(null);
            modalTitle.textContent = "새 게시물 만들기";
            confirmBtn.innerText = "작성"
        } else {
            modalTitle.textContent = "게시물 수정하기";
            confirmBtn.innerText = "수정"
        }
    });

    <!-- 모달 닫기 이벤트 등록 -->
    exampleModal.addEventListener('hide.bs.modal', function (event) {
        // 비워주고.
        $('#message-text').val('');
        $('#imgPreview').empty();
        $('#formFileMultiple').val(null);
        $("input:checkbox[id='onlyMe']").prop("checked", false);
    });

    const fileInput = document.getElementById('formFileMultiple');

    fileInput.addEventListener("change", function (event) {
        var fileName = fileInput.value.split("\\").pop();
        var fileExt = fileName.substring(fileName.lastIndexOf(".")+1);  //확장자 추출
        fileExt = fileExt.toLowerCase();

        if (fileExt != "jpg" && fileExt != "jpeg" && fileExt != "gif" && fileExt != "png"
            && fileExt != "bmp") {
            alert("이미지 파일만 등록이 가능합니다.");
            $('#formFileMultiple').val(null);
            return;
        }

        $('#imgPreview').empty();
        var files = event.target.files;
        console.log(files);
        // 이 코드가 존재하는 함수의 매개변수로 넘어온 값들을 array로 변환하겠다는 뜻이다.
        var arr = Array.prototype.slice.call(files);

        if (arr.length > 5) {
            alert("최대 다섯장까지 업로드 할 수 있습니다.");
            $('#formFileMultiple').val(null);
            return;
        }
        preview(arr);
    });
});

function preview(arr){

    arr.forEach(function (file) {
        // div에 이미지추가.
        var str = '<li class="ui-state-default">';
        if (file.type.match('image.*')) {
            var reader = new FileReader();

            reader.onload = function (e) {
                str += '<img src="'+e.target.result+'" title="'+file.name+'" width=80 height=80>';
                str += '<span class="delBtn" onclick="delImg(this)">x</span>';
                str += '</li>';
                $(str).appendTo('#imgPreview');
            }
            reader.readAsDataURL(file);
        }
    });
}

function delImg(_this){
    const dataTransfer = new DataTransfer();
    let files = $('#formFileMultiple')[0].files;
    let fileArray = Array.from(files);
    let index = $('#imgPreview').find(_this).parent('li').index();
    fileArray.splice(index, 1);
    fileArray.forEach(file => { dataTransfer.items.add(file); });
    $('#formFileMultiple')[0].files = dataTransfer.files;

    $(_this).parent('li').remove();
}

