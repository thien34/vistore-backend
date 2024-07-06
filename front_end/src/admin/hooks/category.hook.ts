import { useMutation, useQuery } from '@tanstack/react-query'
import CategoryService from '../apis/CategoryService'
import { CategoryFilter, CategoryRequest, CategoryResponse } from '@/model/Category'

export const useCategories = (filter: CategoryFilter) => {
    return useQuery({
        queryKey: ['categories', filter.pageNo, filter.name, filter.published],
        queryFn: () => CategoryService.getAll(filter),
    })
}

export const useCategory = (id?: number) => {
    const initialValue: CategoryResponse = {
        name: '',
        description: '',
        pictureId: null,
        published: true,
        showOnHomePage: false,
        includeInTopMenu: true,
        pageSize: 6,
        priceRangeFiltering: true,
        displayOrder: 0,
        categoryParentId: null,
        deleted: false,
        id: 0,
    }
    return useQuery({
        queryKey: ['category', id],
        queryFn: () => (id ? CategoryService.get(id) : initialValue),
        initialData: initialValue,
    })
}

export const useUpdateCategory = () => {
    return useMutation({
        mutationKey: ['updateCategory'],
        mutationFn: (category: CategoryRequest) => CategoryService.update(category),
    })
}

export const useDeleteCategory = () => {
    return useMutation({
        mutationKey: ['deleteCategory'],
        mutationFn: (ids: number[]) => CategoryService.delete(ids),
    })
}

export const useCreateCategory = () => {
    return useMutation({
        mutationKey: ['createCategory'],
        mutationFn: (category: CategoryRequest) => CategoryService.create(category),
    })
}
