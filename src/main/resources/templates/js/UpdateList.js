function updatePatientList(updatedPatients) {
    // Get the UL element
    var ul = document.querySelector("ul");

    // Clear the existing list
    ul.innerHTML = "";

    // Loop through the updated patients and append them to the list
    updatedPatients.forEach(function (patient) {
        var li = document.createElement("li");
        li.setAttribute("th:each", "patient : ${updatedPatients}");
        li.setAttribute("th:data-patient-id", patient.id);

        var span1 = document.createElement("span");
        span1.style.width = "65%";
        span1.style.display = "inline-block";
        span1.style.marginLeft = "30px";
        span1.style.margin = "5px";
        span1.textContent = patient.firstName + " " + patient.lastName;

        var span2 = document.createElement("span");
        span2.style.width = "30%";
        span2.style.display = "inline-block";
        span2.style.marginRight = "30px";
        span2.style.alignSelf = "end";
        span2.style.padding = "5px";
        span2.textContent = patient.gender;

        li.appendChild(span1);
        li.appendChild(span2);
        ul.appendChild(li);
    });
}
