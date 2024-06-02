const nomarlUrl = (url) => {
    return url.startsWith('/') ? url : `/${url}`
}

const convertCurrency = (currency = 'USD') => {
    return currency === 'USD' ? '$' : 'đ'
}

const fromatCurrency = (price, currency = 'USD') => {
    const codes = {
        USD: 'en-US',
        VND: 'vi-VN'
    }
    return new Intl.NumberFormat(codes[currency], {
        style: 'currency',
        currency,
        maximumFractionDigits: 0
    }).format(price)
}

export { nomarlUrl, convertCurrency, fromatCurrency }
