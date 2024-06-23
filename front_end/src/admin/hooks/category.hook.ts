import { useQuery } from '@tanstack/react-query'
import CategoryService from '../apis/CategoryService'
import { CategoryFilter } from '../types/Category'

export const useCategories = (filter: CategoryFilter) => {
    return useQuery({
        queryKey: ['categories', filter.pageNo, filter.name, filter.published],
        queryFn: () => CategoryService.getAll(filter),
    })
}
