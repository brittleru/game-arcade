const divCanvas = document.getElementById("snake-canvas");
const username = document.getElementById("username-id");
const highestScore = document.getElementById("score-id");

const SCL = 20; // size of each tile
let frameRateNum = 10;
let wOs, hOs; // width over SCL, height over SCL

let snake;

let score;
let food;

function setup() {
    const myCanvas = createCanvas(900, 500);
    myCanvas.parent(divCanvas);
    wOs = width / SCL;
    hOs = height / SCL;

    snake = new TileSnake(Math.floor(wOs / 2), Math.floor(hOs / 2));

    score = 0;
    food = newFood();
    textAlign(CENTER);
    textSize(SCL);
    frameRate(frameRateNum);
}

function draw() {
    background(51);

    if (snake.alive) {
        if (snake.update(food)) {
            food = newFood();
            score++;
        }
        snake.draw();
    } else {
        // TODO: add score functionality here
        console.log(score);
        gameOver();
    }

    fill(random(255), 0, random(255));
    rect(food.x * SCL, food.y * SCL, SCL, SCL);
    text(`Score: ${score}`, SCL + 40, SCL + 20);
}

function gameOver() {
    noLoop();
    textSize(60);
    text("You lost!", width / 2, height / 2);
    textSize(30);
    text("Press F5 to replay!", width / 2, height / 2 + 50);
}


function newFood() {
    const x = Math.floor(random(wOs));
    const y = Math.floor(random(hOs));
    return createVector(x, y);
}


function keyPressed() {
    switch (keyCode) {
        case UP_ARROW:
            snake.direct(createVector(0, -1));
            break;
        case DOWN_ARROW:
            snake.direct(createVector(0, 1));
            break;
        case RIGHT_ARROW:
            snake.direct(createVector(1, 0));
            break;
        case LEFT_ARROW:
            snake.direct(createVector(-1, 0));
            break;
    }
}
