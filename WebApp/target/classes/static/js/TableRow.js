/* Represent an HTML row of data */
class TableRow
{
    // Grab an array where each element represents an array of data
    constructor(dataPerCols, isHeaderRow = false)
    {
        this.dataPerCols = dataPerCols;
        this.isHeaderRow = isHeaderRow;
    }

    getHTMLString()
    {
        /* Grab HTML String representation of this row of data */
        let htmlRowRep = "<tr>";

        // Loop through each data column
        for (let dataCol of this.dataPerCols)
        {
            if (this.isHeaderRow)
            {
                const currentHeaderColRep = `<th>${dataCol}</th>`;
                htmlRowRep += currentHeaderColRep;
            }

            else
            {
                const currentDataColRep = `<th>${dataCol}</th>`;
                htmlRowRep += currentDataColRep;
            }
        }

        htmlRowRep += "</tr>";
        return htmlRowRep;
    }
}