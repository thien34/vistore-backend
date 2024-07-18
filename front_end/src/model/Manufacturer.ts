export type ManufacturerResponse ={
    id:  number,
    name: string,
    description: string,
    pictureId: number,
    pageSize: number
    published: boolean
    deleted: boolean
    displayOrder: number
    priceRangeFiltering: boolean
}
export type ManufacturerRequest={
    id?: number,
    name: string,
    description: string,
    pictureId: number | null,
    pageSize: number,
    published: boolean,
    displayOrder: number,
    priceRangeFiltering: boolean
}