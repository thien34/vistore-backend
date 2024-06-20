import { useQuery } from '@tanstack/react-query'
import CategoryService from '../apis/CategoryService'

export const useCategories = () => {
    return useQuery({
        queryKey: ['categories'],
        queryFn: () => CategoryService.getAll({}),
    })
}
