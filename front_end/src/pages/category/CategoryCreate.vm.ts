import useCreateApi from '@/hooks/use-create-api'
import { CategoriesResponse, CategoryParentResponse, CategoryRequest } from '@/model/Category'
import { Form, GetProp, UploadFile, UploadProps } from 'antd'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import CategoryConfigs from './CategoryConfigs'
import useGetAllApi from '@/hooks/use-get-all-api'
import { RequestParams } from '@/utils/FetchUtils'
import useUploadMultipleImagesApi from '@/hooks/use-upload-multiple-images-api'

function useCategoryCreateViewModel() {
    const [form] = Form.useForm()
    type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0]
    const [previewOpen, setPreviewOpen] = useState(false)
    const [previewImage, setPreviewImage] = useState('')
    const navigation = useNavigate()
    const [fileList, setFileList] = useState<UploadFile[]>([])
    const [filter] = useState<Search>({})
    const layout = {
        labelCol: { span: 8 },
        wrapperCol: { span: 16 },
    }

    interface Search extends RequestParams {
        published?: boolean
    }

    const { data } = useGetAllApi<CategoriesResponse>(CategoryConfigs.resourceUrl, CategoryConfigs.resourceKey, filter)
    const { mutateAsync: createPictures, isPending } = useUploadMultipleImagesApi()
    const { mutate: createCategoryApi } = useCreateApi<CategoryRequest, string>(CategoryConfigs.resourceUrl)

    // INITIAL VALUES
    const initialValues: CategoryRequest = {
        name: '',
        description: '',
        categoryParentId: null,
        pictureId: null,
        published: true,
        showOnHomePage: false,
        includeInTopMenu: true,
        pageSize: 6,
        priceRangeFiltering: true,
        displayOrder: 0,
    }

    // HANDLER NAME PARENT CATEGORY
    function getCategoryFullName(category: CategoriesResponse | CategoryParentResponse): string {
        return category.categoryParent
            ? `${getCategoryFullName(category.categoryParent)} >> ${category.name}`
            : category.name
    }

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
        setFileList(newFileList)
    }

    // HANDLER FUNCTIONS FOR FORM COMPONENTS ADD
    const onFinish = async (values: CategoryRequest) => {
        if (fileList.length) {
            try {
                const result = await createPictures([fileList[0].originFileObj as File])
                values.pictureId = result.content[0]
            } catch (error) {
                console.error(error)
            }
        }

        createCategoryApi(values, { onSuccess: () => navigation(-1) })
    }

    return {
        form,
        layout,
        onFinish,
        isPending,
        fileList,
        setFileList,
        handleChange,
        handlePreview,
        previewOpen,
        setPreviewOpen,
        previewImage,
        setPreviewImage,
        data,
        getCategoryFullName,
        initialValues,
    }
}

export default useCategoryCreateViewModel
