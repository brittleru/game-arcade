const username = document.getElementById("username-id");
const highestScore = document.getElementById("score-id");


const gameName = "snake";
const highScoreEndpoint = `/arcadeit/api/v1/high/score/${gameName}/`;
const usersApiEndpoint = "/arcadeit/api/v1/users-basic-info/user/";
const addUpdateHighScoreEndpoint = "/arcadeit/api/v1/high/scores";

function generateJsonForHighScore() {
    return {
        "id": 0,
        "game": "",
        "score": 0,
        "createdAt": null,
        "user": {
            "id": 0
        }
    };
}


async function populateNewHighScore() {
    let user;
    let httpMethod;
    let bodyToPost = generateJsonForHighScore();
    // fetch user and game
    await fetch(`${usersApiEndpoint}${username.innerHTML}`).then(response => {
        return response.json();
    }).then(data => {
        user = data;
    }).catch(error => {
        console.log(error);
    });

    await getUserHighScore().then(userHighScore => {
        if (userHighScore !== 0) {
            httpMethod = "PUT";
            bodyToPost["id"] = userHighScore["id"];
            bodyToPost["score"] = score >= userHighScore["score"] ? score : userHighScore["score"];
            bodyToPost["game"] = gameName;
            bodyToPost["createdAt"] = userHighScore["createdAt"];
            bodyToPost["user"] = userHighScore["user"];
            console.log(`Updating score for user ${username.innerHTML} -> score: ${bodyToPost["score"]}`)
        } else {
            httpMethod = "POST";
            bodyToPost["id"] = 0;
            bodyToPost["score"] = score;
            bodyToPost["game"] = gameName;
            bodyToPost["user"] = user;
            console.log(`Adding score for user ${username.innerHTML} -> score: ${bodyToPost["score"]}`)
        }
    });
    bodyToPost = JSON.stringify(bodyToPost);

    if (score === bodyToPost["score"]) {
        console.log("score: ", score, " is equal to highscore");
        return;
    }

    await fetch(addUpdateHighScoreEndpoint, {
        method: httpMethod,
        headers: {
            "Accept": "*/*",
            "Content-type": "application/json"
        },
        body: bodyToPost
    }).catch(error => {
        console.log(error);
    });
}

async function getUserHighScore() {
    let userHighScoreDb;

    await fetch(`${highScoreEndpoint}${username.innerHTML}`, {method: "GET"}).then(response => {
        if (response["status"] === 204) {
            return 0;
        }
        return response.json();
    }).then(data => {
        userHighScoreDb = data;
    }).catch(error => {
        console.log(error);
    });

    return userHighScoreDb;
}

getUserHighScore().then(userHighScoreDb => {
    highestScore.innerHTML = userHighScoreDb === 0 ? userHighScoreDb : userHighScoreDb["score"];
});

