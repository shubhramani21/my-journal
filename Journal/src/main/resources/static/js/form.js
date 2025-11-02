document.addEventListener("DOMContentLoaded", async () => {
    const form = document.querySelector("#form");
    const title = document.querySelector("#title");
    const entryInput = document.querySelector("#entry");
    const backBtn = document.querySelector("#back");
    //// 4:33 am done make sure you check submit button and edit methods 
    const submitDiv = document.querySelector("#submit");

    const isEditMode = entryId && entryId !== "null" && entryId.trim() !== "";

    backBtn.addEventListener("click", () => {
        window.location.href = "/entries";
    });


    if (isEditMode) {
        try {
            const response = await fetch(`/journal/id/${entryId}`, {
                method: "GET",
                credentials: "same-origin"
            });

            if (response.ok) {
                const entry = await response.json();
                title.value = entry.title || '';
                entryInput.value = entry.content || '';

            } else {
                throw new Error("Failed to fetch entry");
            }
        } catch (e) {
            console.error("Error fetching entry:", e);
        }
    }


    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const titleValue = title.value;
        const contentValue = entryInput.value;


        const url = isEditMode ? `/journal/id/${entryId}` : `/journal/new`;


        const method = isEditMode ? "PUT" : "POST";

        try {
            const response = await fetch(url, {
                method,
                headers: { "Content-Type": "application/json" },
                credentials: "same-origin",
                body: JSON.stringify({ title: titleValue, content: contentValue }),
            });

            if (response.ok) {
                alert(isEditMode ? "Entry updated!" : "Entry created!");
                window.location.href = "/entries";
            } else {
                alert("Failed to save entry");
            }
        } catch (e) {
            console.error("Error submitting form:", e);
        }
    })
})