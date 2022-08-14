function sendNewLikesRequest(postId) {

    fetch('/likes/new', {
        method: 'post',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
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


