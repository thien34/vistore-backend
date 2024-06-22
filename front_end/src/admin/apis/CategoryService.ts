import { http } from '@/libs/http'
import { CategoryFilter, CategoryRequest, CategoryResponseBasic, CategoryResponseWithPage } from '../types/Category'

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

    async create(request: CategoryRequest) {
        const url = '/admin/categories'
        const result = await http.post<CategoryResponseBasic>(url, request)
        return { status: result.payload.status, message: result.payload.message }
    }
}

export default new CategoryService()
