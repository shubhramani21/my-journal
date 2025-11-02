function formatDate(dateString) {
  try {
    const date = new Date(dateString);
    if (isNaN(date.getTime())) return "Invalid date";
    else
      return date.toLocaleDateString("en-US", {
        year: "numeric",
        month: "long",
        day: "numeric",
      });
  } catch (error) {
    return "Invalid Date";
  }
}

async function loadEntries() {
  try {
    const response = await fetch(`/journal`, {
      method: "GET",
      credentials: "same-origin",
    });

    const loading = document.querySelector(".loading");
    const cardList = document.querySelector("#card-list");
    const emptyState = document.querySelector("#elements .emptyState");

    if (response.ok) {
      const entries = await response.json();

      loading.style.display = "none";

      var isEmpty = checkEntriesState(entries);
      if (!isEmpty) {
        cardList.innerHTML = entries
          .map(
            (entry) => `
                    <div class="card" onclick="editEntry('${entry.id}')">
                        <div class="title">
                            <h2>
                                ${entry.title
                                  .split(" ")
                                  .map((word) => `<span>${word}</span>`)
                                  .join(" ")}
                            </h2>
                            <p class="date">${formatDate(entry.date)}</p>
                        </div>

                        <div class="entry-content">
                            <p>${entry.content}</p>
                        </div>

                        <div class="delete" onclick="deleteEntry('${
                          entry.id
                        }')">
                            <i class="ri-close-line"></i>
                        </div>
                    </div>

                `
          )
          .join("");
      }
    } else if (response.status === 404) {
      loading.style.display = "none";
      emptyState.style.display = "block";
    } else {
      loading.innerHTML = "Error loading entries";
    }
  } catch (error) {
    console.error("Error fetching entries:", error);
    loading.innerText = "Error loading entries.";
  }
}

async function deleteEntry(entryId) {
  if (!confirm("Are you sure you want to delete this entry?")) return;

  try {
    const response = await fetch(`/journal/${entryId}`, {
      method: "DELETE",
      credentials: "same-origin",
    });

    console.log("Deleting entry", entryId);

    if (response.ok || response.status === 204) {
      location.reload();
    } else {
      throw new Error("Failed to delete entry");
    }
  } catch (e) {
    alert("Error deleting entry.");
  }
}



async function editEntry(entryId) {
  try {
    console.log("editing entry", entryId);
    window.location.href = `/entries/edit/${entryId}`;
  } catch (error) {
    alert("Error updating entry.");
  }
}


document.addEventListener("DOMContentLoaded", loadEntries);
