const elementsContainer = document.querySelector("#elements");
const loadingContainer = document.querySelector(".loading");


// 1. trim function
function trimInput(input) {
    const maxLen = 170;
    return input.length > maxLen ? input.slice(0, maxLen) + "..." : input;
}

function trimContent() {
    const cards = document.querySelectorAll(".card .entry-content p");
    cards.forEach((para) => {
        para.innerText = trimInput(para.innerText);
    });
}

// 2. setup profile menu
function setupProfileMenu() {
    const profileIcon = document.querySelector("#profile-icon");
    const signOutMenu = document.querySelector("#signOutMenu");
    const signOutButton = document.querySelector("#signOutButton");

    if (!profileIcon || !signOutMenu || !signOutButton) {
        return;
    }

    // Initially hide the menu
    signOutMenu.style.display = "none";

    profileIcon.addEventListener("click", (event) => {
        event.stopPropagation();
        signOutMenu.style.display =
            signOutMenu.style.display === "none" ? "block" : "none";
    });
    
    signOutButton.addEventListener("click", () => {
        signOutMenu.style.display = "none";
    });


    document.addEventListener("click", (event) => {
        if (
            !profileIcon.contains(event.target) &&
            !signOutMenu.contains(event.target)
        ) {
            signOutMenu.style.display = "none";
        }
    });
}

// check entries state
function checkEntriesState(cardElements) {
    const cardList = document.querySelector("#card-list");
    const emptyState = document.querySelector(".emptyState");


    loadingContainer.style.display = "none";
    if (cardElements.length === 0) {
        console.log("no entries");
        emptyState.style.display = "block";
        cardList.style.display = "none";
        return true;

    }

    console.log("entries found");
    emptyState.style.display = "none";
    cardList.style.display = "block";

    return false;
    
    
}

document.addEventListener("DOMContentLoaded", () => {
    setupProfileMenu();
    trimContent();
    const cardElements = document.querySelectorAll("#card-list .card");
    console.log(cardElements);
    checkEntriesState(cardElements);
});
