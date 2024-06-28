export type ProductTagResponse = {
    id: number
    name: string
    productId: number
}

export type ProductTagRequest = {
    id?: number
    name: string
    productId: number
}

export type ProductTagResponseWithPage = {
    status: number
    message: string
    data: {
        page: number
        size: number
        totalPage: number
        items: ProductTagResponse[]
    }
}

export type ProductTagResponseBasic = {
    status: number
    message: string
}

export interface ProductTagFilter {
    name?: string
    pageNo?: number
    pageSize?: number
    sortBy?: string
}
