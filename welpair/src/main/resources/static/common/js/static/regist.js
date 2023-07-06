window.onload = function () {



    if(document.getElementById("numCheck")){

        const $duplication = document.getElementById("numCheck");

        $duplication.onclick = function(){
            let memberNo = document.getElementById("memberNo").value.trim();  // 띄어쓰기없는 사원번호 담아봐

        fetch("/member/idDupCheck",{

            method: "POST",
            headers: {
                'Content-Type': 'application/json;charset-UTF-8'
            },
            body: JSON.stringify({memberNo:memberNo})
        })
            .then(result => result.text())
            .then(result => alert(result))
            .catch((error) => error.text().then((res)=> alert(res)));



        }



    }

































}