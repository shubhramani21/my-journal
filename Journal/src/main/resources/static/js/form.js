const entryId = /*[[${entryId}]]*/ "";
console.log("Entry Id: ", entryId);

const form = document.querySelector("#form");
const title = document.querySelector("#title");
const entryInput = document.querySelector("#entry");
const backBtn = document.querySelector("#back");
const submitDiv = document.querySelector("#submit");

const safeEntryId = entryId ? entryId.toString().trim() : "null";

const isEditMode = safeEntryId !== "null" && safeEntryId !== "";
console.log("Is edit mode:", isEditMode);

const url = isEditMode ? `/journal/id/${safeEntryId}` : `/journal/new`;
const methodWay = isEditMode ? "PUT" : "POST";

console.log("URL:", url);
console.log("Method:", methodWay);

async function loadEntry() {
    try {
        const response = await fetch(`/journal/id/${safeEntryId}`, {
            method: "GET",
            credentials: "same-origin",
        });

        if (response.ok) {
            const entry = await response.json();

            document.querySelector("#title").value = entry.title || "";
            document.querySelector("#content").value = entry.content || "";
        } else {
            console.error("Failed to load entry data", response.status);
        }
    } catch (error) {
        console.error("Error loading entry:", error);
    }
}

if (isEditMode) {
    loadEntry();
}

submitDiv.addEventListener("click", () => {
    form.requestSubmit();
});

backBtn.addEventListener("click", () => {
    window.location.href = "/entries";
});

form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const titleValue = title.value.trim();
    const contentValue = entryInput.value;

    try {
        const response = await fetch(url, {
            method: methodWay,
            headers: { "Content-Type": "application/json" },
            credentials: "same-origin",
            body: JSON.stringify({
                title: titleValue,
                content: contentValue,
            }),
        });

        if (response.ok) {
            alert(isEditMode ? "Entry updated!" : "Entry created!");
            window.location.href = "/entries";
        } else {
            throw new Error("Failed to submit form");
        }
    } catch (e) {
        alert("Error submitting form: " + e.message);
    }
});
