// Global Constants
const backEndGetPostingsUrlEndpoint = "https://sample-job-harvester.onrender.com/api/jobPostings";

function main()
{
    /* Perform an AJAX request to grab data from the back-end */
    $.ajax({
        url: backEndGetPostingsUrlEndpoint,
        method: "GET",
        beforeSend: () => hideTable(),
        success: (response) => {

            // Add all job postings object from the response to the allJobPostings array
            let allJobPostings = [];

            for (let jobPosting of response)
            {
                allJobPostings.push(jobPosting);
            }

            // Run the following function once we received all job postings
            runWhenSuccessful(allJobPostings);

        },
        error: () => errorAlert("Could not grab job postings due to a network error"),
        complete: () => {
            showTable();
        }
    });
}

function hideTable()
{
    /* Hide table container and show spinner */
    // Grab table and spinner
    const tableContainerElement = $("div#job-postings-table-container");
    const spinnerElement = $("div#spinner-container");

    // Hide the table and show the spinner
    tableContainerElement.hide();
    spinnerElement.show();
}
function showTable()
{
    /* Show table container and hide spinner */
    // Grab table and spinner
    const tableContainerElement = $("div#job-postings-table-container");
    const spinnerElement = $("div#spinner-container");

    // Show the table and hide the spinner
    tableContainerElement.show();
    spinnerElement.hide();
}

function capitalizeFirstLetterOfString(a_string)
{
    /* Capitalize the First Letter of the given string */
    return a_string.replace(a_string[0], a_string[0].toUpperCase());
}

function formatDate(dateString)
{
    /* Format String in the way YYYY-MM-DD */
    const date = new Date(dateString);
    const month = Number(date.getMonth()) < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1;
    const day = Number(date.getDate()) < 10 ? `0${date.getDate()}` : date.getDate();
    return `${date.getFullYear()}-${month}-${day}`;
}

function getTableHeaders(jobPosting)
{
    // Grab relevant table headers (exclude id header)
    const allKeys = Object.keys(jobPosting);
    return allKeys.slice(1, allKeys.length);
}

function getJobPostingData(jobPosting)
{
    const allValues = Object.values(jobPosting);
    return allValues.slice(1, allValues.length);
}

function runWhenSuccessful(jobPostings)
{
    /* Run when we could successfully grab job Postings */

    // Display table headers
    displayTableHeaders(jobPostings);

    // Display job postings data
    displayJobPostingsData(jobPostings);

    // Grab the input element that filters job postings
    const searchInputElement = $("input#search-input");

    // Listen to the change event in this input element
    searchInputElement.on("input", function() {

        // Grab the current value of the input element
        const currentValue = searchInputElement.val();

        // Remove all data from job postings
        removeAllJobPostingData();

        // Display all job postings and return early when there is no value
        if (!Boolean(currentValue))
        {
            displayJobPostingsData(jobPostings);
            return;
        }

        // Now grab all job postings that fit with the user's role
        const filteredJobPostings = filteredJobPostingsByRole(jobPostings, currentValue);

        // If there is no job posting that fits then display all job postings and return early
        if (filteredJobPostings.length == 0)
        {
            displayJobPostingsData(jobPostings);
            return;
        }

        // By now show only the filtered job postings
        displayJobPostingsData(filteredJobPostings);

    });
}

function filteredJobPostingsByRole(jobPostings, filteringString)
{
    // Filter Job Postings By Role
    const validJobPostings = [];

    jobPostings.forEach(jobPosting => {
        if (jobPosting["role"].toLowerCase().includes(filteringString.toLowerCase()))
        {
            validJobPostings.push(jobPosting);
        }
    });

    return validJobPostings;
}

function displayTableHeaders(jobPostings)
{
    // Grab headers for job postings
    const tableHeaders = getTableHeaders(jobPostings[0]);

    // Capitalize first letter of all headers
    const capitalizedTableHeaders = tableHeaders.map(tableHeader => capitalizeFirstLetterOfString(tableHeader));

    // Grab HTML representation of this row of header data
    const jobPostingHTMLHeaderRowRep = new TableRow(capitalizedTableHeaders, true).getHTMLString();

    // Add the headers to the table
    addToTable(jobPostingHTMLHeaderRowRep, true);
}

function displayJobPostingsData(jobPostings)
{
    // Loop through each Job Posting
    jobPostings.forEach(jobPosting => {

        // Grab data of each job posting as an array
        const jobPostingDataAsArray = getJobPostingData(jobPosting);

        // Format the date so it looks more readable
        jobPostingDataAsArray[jobPostingDataAsArray.length - 1] = formatDate(jobPostingDataAsArray[jobPostingDataAsArray.length - 1]);

        // Grab HTML representation of each job Posting as a row of data
        const jobPostingHTMLDataRowRep = new TableRow(jobPostingDataAsArray).getHTMLString();

        // Add the HTML representation to the table
        addToTable(jobPostingHTMLDataRowRep);
    });
}

function removeAllJobPostingData()
{
    /* remove all data that has to do with job postings */

    // Grab the table body
    const jobPostingsTableBody = $("tbody#table-body");

    // Empty it
    jobPostingsTableBody.empty();
}


function addToTable(htmlRowRep, addToHeader = false)
{
    // Grab the table to deal with
    const tableElement = $("table#job-postings-table");

    if (addToHeader)
    {
        const tableHeaderElement = $("thead#table-headers");
        tableHeaderElement.append(htmlRowRep);
    }

    else
    {
        const tableBodyElement = $("tbody#table-body");
        tableBodyElement.append(htmlRowRep);
    }
}

// Execute once the DOM loads
$(document).ready(main);