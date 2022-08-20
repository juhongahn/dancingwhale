function sendNewLikesRequest(postId) {

    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    fetch('/likes/new', {
        method: 'post',
        headers: {
            'header': header,
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-Requested-With': 'XMLHttpRequest',
            'X-CSRF-Token': token
        },
        body: new URLSearchParams({
            'postId': postId
        })
    })
        .then(res => res.json())
        .then(data => {
            alert(data)
            window.location.reload();
        })

}


