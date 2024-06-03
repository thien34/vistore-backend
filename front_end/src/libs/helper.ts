const nomarlUrl = (url: string) => {
    return url.startsWith('/') ? url : `/${url}`
}

export { nomarlUrl }
