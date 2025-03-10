document.addEventListener("DOMContentLoaded", () => {
  const getRatesBtn = document.querySelector(".get-rates-btn");
  const nextStepBtn = document.querySelector(".step-two button");

  const stepOne = document.querySelector(".step-one");
  const stepTwo = document.querySelector(".step-two");
  const stepThree = document.querySelector(".step-three");
  const loader = document.querySelector(".loader");
  const ratesTableBody = document.querySelector(".carrier-table tbody");
  const originProvinceSelect = document.querySelector(".origin-province");
  const destinationProvinceSelect = document.querySelector(
    ".destination-province"
  );

  let availableRates = [];
  const url = "http://localhost:9000/api";
  // Provinces
  const provinces = [
    "ON",
    "QC",
    "BC",
    "AB",
    "MB",
    "SK",
    "NS",
    "NB",
    "PE",
    "NL",
  ];

  // Function to populate the provinces dynamically
  const populateProvinces = (selectElement) => {
    provinces.forEach((province) => {
      const option = document.createElement("option");
      option.value = province;
      option.textContent = province;
      selectElement.appendChild(option);
    });
  };

  // Populate provinces for both origin and destination
  populateProvinces(originProvinceSelect);
  populateProvinces(destinationProvinceSelect);

  // Fields to validate
  const requiredFields = [
    ".origin-address-line",
    ".origin-city",
    ".origin-province",
    ".origin-company",
    ".origin-attention",
    ".origin-postal-code",
    ".origin-country",
    ".origin-phone",
    ".destination-address-line",
    ".destination-city",
    ".destination-province",
    ".destination-company",
    ".destination-attention",
    ".destination-postal-code",
    ".destination-country",
    ".destination-phone",
  ];

  // List of required dimension fields
  const requiredNumberFields = [".length", ".width", ".height", ".weight"];

  getRatesBtn.disabled = true;

  // Function to validate Step 1 form
  const validateStepOneForm = () => {
    let isValid = true;

    requiredFields.forEach((selector) => {
      const field = document.querySelector(selector);
      if (!field) return;

      // Validate text inputs
      if (
        (field.type === "text" || field.type === "number") &&
        !field.value.trim()
      ) {
        field.style.border = "2px solid red";
        isValid = false;
      }
      // Validate select fields (province & country must be selected)
      else if (field.tagName === "SELECT") {
        if (field.value === "" || field.value === "country") {
          field.style.border = "2px solid red";
          isValid = false;
        } else {
          field.style.border = "1px solid #ccc";
        }
      } else {
        field.style.border = "1px solid #ccc";
      }
    });

    // Validate dimension fields (length, width, height, weight)
    requiredNumberFields.forEach((selector) => {
      const field = document.querySelector(selector);
      if (!field) return;

      const value = parseFloat(field.value);
      if (isNaN(value) || value <= 0) {
        field.style.border = "2px solid red";
        isValid = false;
      } else {
        field.style.border = "1px solid #ccc";
      }
    });

    getRatesBtn.disabled = !isValid;
  };

  requiredFields.forEach((selector) => {
    const field = document.querySelector(selector);
    if (!field) return;

    if (field.type === "text" || field.type === "number") {
      field.addEventListener("input", validateStepOneForm);
    } else if (field.tagName === "SELECT") {
      field.addEventListener("change", validateStepOneForm);
    }
  });

  requiredNumberFields.forEach((selector) => {
    const field = document.querySelector(selector);
    if (field) {
      field.addEventListener("input", validateStepOneForm);
      field.addEventListener("change", validateStepOneForm);
    }
  });

  const fetchData = async () => {
    stepOne.style.display = "none";
    loader.style.display = "block";

    // Gather form values
    const requestBody = {
      fromAddress: {
        companyName: document.querySelector(".origin-company").value,
        streetAddress: document.querySelector(".origin-address-line").value,
        city: document.querySelector(".origin-city").value,
        countryCode: document.querySelector(".origin-country").value,
        state: document.querySelector(".origin-province").value,
        postalCode: document.querySelector(".origin-postal-code").value,
        attention: document.querySelector(".origin-attention").value,
        phone: document.querySelector(".origin-phone").value,
      },
      toAddress: {
        companyName: document.querySelector(".destination-company").value,
        streetAddress: document.querySelector(".destination-address-line")
          .value,
        city: document.querySelector(".destination-city").value,
        countryCode: document.querySelector(".destination-country").value,
        state: document.querySelector(".destination-province").value,
        postalCode: document.querySelector(".destination-postal-code").value,
        attention: document.querySelector(".destination-attention").value,
        phone: document.querySelector(".destination-phone").value,
      },
      packageType: "PACKAGE",
      lineItems: [
        {
          length: parseFloat(document.querySelector(".length").value),
          width: parseFloat(document.querySelector(".width").value),
          height: parseFloat(document.querySelector(".height").value),
          weight: parseFloat(document.querySelector(".weight").value),
          declaredValue: {
            currency: "CAD",
            amount: 0,
          },
        },
      ],
      unitOfMeasurement: "METRIC",
      shipDate: new Date().toISOString(),
    };

    try {
      const response = await fetch(`${url}/rates`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestBody),
      });

      if (!response.ok) {
        const errorData = await response.json();
        if (errorData.errors && Array.isArray(errorData.errors)) {
          const errorMessage = errorData.errors.join("\n");
          alert(`Error(s) occurred:\n${errorMessage}`);
        } else {
          alert("An unknown error occurred. Please try again.");
        }
        stepOne.style.display = "flex";
        return;
      }
      const data = await response.json();

      availableRates = data;
      renderRatesTable(data.availableRates);
    } catch (error) {
      console.error("Error fetching rates:", error);
      alert("Failed to fetch rates. Please try again.");
    } finally {
      loader.style.display = "none";
    }
  };

  // Function to render rates in the Step 2 table
  const renderRatesTable = (rates) => {
    loader.style.display = "none";
    stepOne.style.display = "none";
    stepTwo.style.display = "block";

    ratesTableBody.innerHTML = "";

    rates.forEach((rate) => {
      const totalAmount = rate.totalCharge.amount;
      const currency = rate.totalCharge.currency;

      const row = document.createElement("tr");
      row.innerHTML = `
        <td>
          <input type="radio" name="option" class="carrier-option" value="${rate.quoteId}">
          ${rate.carrierName}
        </td>
        <td>${rate.serviceName}</td>
        <td>${totalAmount} ${currency}</td>
      `;

      ratesTableBody.appendChild(row);
    });

    const nextStepBtn = document.querySelector(".step-two button");
    nextStepBtn.disabled = true;

    document.querySelectorAll(".carrier-option").forEach((radio) => {
      radio.addEventListener("change", () => {
        nextStepBtn.disabled = false;
      });
    });
  };

  const sendData = async () => {
    const selectedOption = document.querySelector(".carrier-option:checked");

    const selectedQuoteId = selectedOption.value;

    const selectedRate = availableRates.availableRates.find(
      (rate) => rate.quoteId === selectedQuoteId
    );

    if (!selectedRate) {
      alert("Invalid selection. Please try again.");
      stepOne.style.display = "flex";
      return;
    }

    const requestBody = {
      carrierId: selectedRate.carrierId,
      carrierName: selectedRate.carrierName,
      serviceId: selectedRate.serviceId,
      serviceName: selectedRate.serviceName,
      quoteId: selectedRate.quoteId,
    };

    stepTwo.style.display = "none";
    loader.style.display = "flex";

    try {
      const response = await fetch(`${url}/shipments`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestBody),
      });

      const data = await response.json();

      renderStepThree(data);

      loader.style.display = "none";
      stepThree.style.display = "block";
    } catch (error) {
      alert("Failed to create shipment. Please try again.");
      stepOne.style.display = "flex";
    } finally {
      loader.style.display = "none";
    }
  };

  // Function to render Step 3
  const renderStepThree = (data) => {
    const shipIdElement = document.querySelector(
      ".step-three .step-three-inner p:nth-of-type(1)"
    );
    const trackingNumberElement = document.querySelector(
      ".step-three .step-three-inner p:nth-of-type(2)"
    );
    const downloadButton = document.querySelector(".step-three button");

    const { shipId, trackingNumbers, labelUrl } = data;

    shipIdElement.textContent = `ShipId: ${shipId}`;
    trackingNumberElement.textContent = `Tracking #: ${trackingNumbers[0]}`;

    downloadButton.addEventListener("click", () => {
      window.location.href = labelUrl;
    });
  };

  getRatesBtn.addEventListener("click", fetchData);
  nextStepBtn.addEventListener("click", sendData);
});