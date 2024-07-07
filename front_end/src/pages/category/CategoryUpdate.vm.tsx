import useUpdateApi from '@/hooks/use-update-api'
import { CategoryRequest, CategoryResponse } from '@/model/Category'
import { useNavigate, useParams } from 'react-router-dom'
import CategoryConfigs from './CategoryConfigs'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import { useGetPicture } from '@/admin/hooks/picture.hook'

function useCategoryUpdateViewModel() {
    const navigation = useNavigate()
    const { id } = useParams<{ id: string }>()
    const isUpdateMode = Boolean(id)
    const { mutate: updateCategory, isPending } = useUpdateApi<CategoryRequest, string>(
        CategoryConfigs.resourceUrl,
        CategoryConfigs.resourceKey,
        Number(id),
    )
    const { data: categoryResponse, isLoading } = useGetByIdApi<CategoryResponse>(
        CategoryConfigs.resourceUrl,
        CategoryConfigs.resourceKey,
        Number(id),
    )
    const pictureResponse = useGetPicture(categoryResponse?.pictureId ?? undefined)
    const onFinish = async (values: CategoryRequest) => {
        if (isUpdateMode) {
            updateCategory({ id: Number(id), ...values }, { onSuccess: () => navigation(-1) })
        }
    }
    return { pictureResponse, onFinish, categoryResponse, isPending, isLoading }
}
export default useCategoryUpdateViewModel
