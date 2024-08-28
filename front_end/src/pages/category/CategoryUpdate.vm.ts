import useUpdateApi from '@/hooks/use-update-api'
import { CategoryRequest, CategoryResponse } from '@/model/Category'
import { useNavigate, useParams } from 'react-router-dom'
import CategoryConfigs from './CategoryConfigs'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import { PictureResponse } from '@/model/Picture'
import PictureConfigs from '../picture/PictureConfigs'
import useUploadMultipleImagesApi from '@/hooks/use-upload-multiple-images-api'
import { GetProp, UploadFile, UploadProps } from 'antd'
import { useEffect, useState } from 'react'

function useCategoryUpdateViewModel() {
    const navigation = useNavigate()
    type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0]
    const [fileList, setFileList] = useState<UploadFile[]>([])
    const [previewOpen, setPreviewOpen] = useState(false)
    const [previewImage, setPreviewImage] = useState('')

    // SET TITLE
    useEffect(() => {
        document.title = 'Edit category details - Vítore'
    }, [])

    const { id } = useParams<{ id: string }>()

    const { mutate: updateCategory } = useUpdateApi<CategoryRequest>(
        CategoryConfigs.resourceUrl,
        CategoryConfigs.resourceKey,
        Number(id),
    )
    const { data: categoryResponse, isLoading } = useGetByIdApi<CategoryResponse>(
        CategoryConfigs.resourceUrl,
        CategoryConfigs.resourceKey,
        Number(id),
    )
    const { data: pictureResponse } = useGetByIdApi<PictureResponse>(
        PictureConfigs.resourceUrl,
        PictureConfigs.resourceKey,
        categoryResponse?.pictureId ?? 0,
    )
    const { mutateAsync: createPictures, isPending } = useUploadMultipleImagesApi()

    //
    const getBase64 = (file: FileType): Promise<string> =>
        new Promise((resolve, reject) => {
            const reader = new FileReader()
            reader.readAsDataURL(file)
            reader.onload = () => resolve(reader.result as string)
            reader.onerror = (error) => reject(new Error(error.total.toString()))
        })

    const handlePreview = async (file: UploadFile) => {
        if (!file.url && !file.preview) {
            file.preview = await getBase64(file.originFileObj as FileType)
        }
        setPreviewImage(file.url ?? (file.preview as string))
        setPreviewOpen(true)
    }

    //
    const handleChange: UploadProps['onChange'] = ({ fileList: newFileList }) => {
        setFileList(
            newFileList.map((file) => {
                return { ...file, status: 'done' }
            }),
        )
    }

    // HANDLER FUNCTION ON FINISH FORM
    const onFinish = async (values: CategoryRequest) => {
        if (fileList.length > 0) {
            if (fileList[0].originFileObj) {
                const result = await createPictures([fileList[0].originFileObj as File])
                values.pictureId = result.content[0]
            } else {
                values.pictureId = pictureResponse?.id ?? values.pictureId
            }
        } else {
            values.pictureId = null
        }
        updateCategory({ id: Number(id), ...values }, { onSuccess: () => navigation(-1) })
    }

    return {
        pictureResponse,
        onFinish,
        categoryResponse,
        isPending,
        isLoading,
        handlePreview,
        handleChange,
        fileList,
        setFileList,
        previewOpen,
        setPreviewOpen,
        previewImage,
        setPreviewImage,
    }
}
export default useCategoryUpdateViewModel
