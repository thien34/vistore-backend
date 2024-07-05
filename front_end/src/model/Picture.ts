export type PictureResponse = {
    status: string
    message: string
    data: number[]
}

export type PictureResponseBase = {
    status: string
    message: string
    data: {
        id: number
        mimeType: string
        linkImg: string
    }
}
