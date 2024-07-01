export type PictureRequest = {
    images: File[]
}
export type PictureResponse = {
    id: number[]
}

export type PictureResponseBasic = {
    status: string
    message: string
    data: PictureResponse
}
