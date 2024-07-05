import { http } from '@/libs/http'
import {
    CategoryFilter,
    CategoryRequest,
    CategoryResponse,
    CategoryResponseBasic,
    CategoryResponseWithPage,
} from '@/model/Category'

class CategoryService {
    async getAll(filter: CategoryFilter) {
        const queryParams = new URLSearchParams({
            name: filter.name ?? '',
            pageNo: filter.pageNo?.toString() ?? '1',
            pageSize: filter.pageSize?.toString() ?? '6',
            published: filter.published?.toString() ?? '',
        })
        const url = `/admin/categories?${queryParams.toString()}`
        const result = await http.get<CategoryResponseWithPage>(url)
        return result.payload
    }

    async get(id: number) {
        const url = `/admin/categories/${id}`
        const result = await http.get<{
            data: CategoryResponse
        }>(url)
        return result.payload.data
    }

    async create(request: CategoryRequest) {
        const url = '/admin/categories'
        const result = await http.post<CategoryResponseBasic>(url, request)
        return { status: result.payload.status, message: result.payload.message }
    }

    async update(request: Partial<CategoryRequest>) {
        const url = `/admin/categories/${request.id}`
        const result = await http.put<CategoryResponseBasic>(url, request)
        return { status: result.payload.status, message: result.payload.message }
    }

    async delete(ids: number[]) {
        const url = '/admin/categories'
        const result = await http.delete<CategoryResponseBasic>(url, ids)
        return { status: result.payload.status, message: result.payload.message }
    }
}

export default new CategoryService()
