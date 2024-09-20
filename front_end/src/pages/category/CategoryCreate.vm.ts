import useCreateApi from '@/hooks/use-create-api'
import { CategoryNameResponse, CategoryRequest } from '@/model/Category'
import { Form, GetProp, UploadFile, UploadProps } from 'antd'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import CategoryConfigs from './CategoryConfigs'
import useUploadMultipleImagesApi from '@/hooks/use-upload-multiple-images-api'
import useGetApi from '@/hooks/use-get-api'

function useCategoryCreateViewModel() {
    const [form] = Form.useForm()
    type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0]
    const [previewOpen, setPreviewOpen] = useState(false)
    const [previewImage, setPreviewImage] = useState('')
    const navigation = useNavigate()
    const [fileList, setFileList] = useState<UploadFile[]>([])
    const layout = {
        labelCol: { span: 8 },
        wrapperCol: { span: 16 },
    }

    // SET TITLE
    useEffect(() => {
        document.title = 'Add a new category - Vítore'
    }, [])

    const data = useGetApi<CategoryNameResponse[]>(
        `${CategoryConfigs.resourceUrl}/list-name`,
        CategoryConfigs.resourceKey,
    ).data
    const { mutateAsync: createPictures, isPending } = useUploadMultipleImagesApi()
    const { mutate: createCategoryApi } = useCreateApi<CategoryRequest>(CategoryConfigs.resourceUrl)

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

    const getCategoryFullName = (category: CategoryNameResponse, parentName = '') => {
        const fullName = parentName ? `${parentName} > ${category.name}` : category.name
        let options = [{ label: fullName, value: category.id }]

        if (category.children && category.children.length > 0) {
            category.children.forEach((child) => {
                options = [...options, ...getCategoryFullName(child, fullName)]
            })
        }
        return options
    }

    const categoryOptions = data?.flatMap((item) => getCategoryFullName(item)) ?? []

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
        handleChange,
        handlePreview,
        previewOpen,
        setPreviewOpen,
        previewImage,
        setPreviewImage,
        initialValues,
        categoryOptions,
    }
}

export default useCategoryCreateViewModel
