import { ProductTagFilter, ProductTagRequest, ProductTagResponseBasic } from './../types/ProductTag'
import { http } from '@/libs/http'
import { ProductTagResponseWithPage } from '../types/ProductTag'

class ProductTagService {
    async getAll(filter: ProductTagFilter): Promise<{ status: number; data: ProductTagResponseWithPage }> {
        try {
            const { name = '', pageNo = 0, pageSize = 6, sortBy = 'id' } = filter
            const requestInit: RequestInit = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            }

            const queryParams = new URLSearchParams({
                name,
                pageNo: pageNo.toString(),
                pageSize: pageSize.toString(),
                sortBy,
            })

            const response = await http.get<ProductTagResponseWithPage>(
                `/product-tags?${queryParams.toString()}`,
                requestInit,
            )
            return { status: response.status, data: response.payload }
        } catch (error) {
            console.error('Error getting product tags:', error)
            throw error
        }
    }

    async create(request: ProductTagRequest): Promise<{ status: number; message: string }> {
        try {
            const response = await http.post<ProductTagResponseBasic>('/product-tags', request)
            return { status: response.payload.status, message: response.payload.message }
        } catch (error) {
            console.error('Error creating product tag:', error)
            throw error
        }
    }
    async delete(ids: number[]): Promise<{ status: number; message: string }> {
        try {
            const response = await http.delete<{ status: number; message: string }>('/product-tags', ids)
            return { status: response.payload.status, message: response.payload.message }
        } catch (error) {
            console.error('Error deleting product tags:', error)
            throw error
        }
    }
}

export default new ProductTagService()
