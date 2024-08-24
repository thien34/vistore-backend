// Define the function
const formatDate = (dateString: Date): string => {
    // Ensure dateString is a valid Date object or convert to Date
    const date = new Date(dateString)

    // Define formatting options
    const options: Intl.DateTimeFormatOptions = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: true,
    }

    // Format the date
    return new Intl.DateTimeFormat('en-US', options).format(date)
}

// Export the function as default
export default formatDate
