import { useNavigate } from 'react-router-dom'
import { Form, Modal } from 'antd'
import useCreateApi from '@/hooks/use-create-api.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'

const useSpecificationAttributeGroupCreateViewModel = () => {
    const [form] = Form.useForm()
    const navigate = useNavigate()
    const { mutate: createSpecificationAttributeGroup } = useCreateApi(SpecificationAttributeGroupConfigs.resourceUrl)

    const layout = {
        labelCol: { span: 8 },
        wrapperCol: { span: 16 },
    }

    const handleSave = () => {
        form.validateFields().then((values) => {
            createSpecificationAttributeGroup(values, {
                onSuccess: () => {
                    navigate(-1)
                },
            })
        })
    }

    const showSaveConfirm = () => {
        Modal.confirm({
            title: 'Are you sure you want to save?',
            content: 'Please confirm if you want to save the changes.',
            onOk: () => handleSave,
        })
    }

    return {
        form,
        showSaveConfirm,
        layout,
    }
}

export default useSpecificationAttributeGroupCreateViewModel
