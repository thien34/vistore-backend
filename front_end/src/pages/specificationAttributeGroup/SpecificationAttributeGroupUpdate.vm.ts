import { useNavigate, useParams } from 'react-router-dom'
import useGetByIdApi from '@/hooks/use-get-by-id-api.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import { Form, Modal } from 'antd'
import { useCallback, useEffect, useState } from 'react'
import useUpdateApi from '@/hooks/use-update-api.ts'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute.ts'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api.ts'
import ManagerPath from '@/constants/ManagerPath'
import { SpecificationAttributeGroupResponse } from '@/model/SpecificationAttributeGroup'
import { getSpecAttrUnGroupCols } from './SpecAttrGroupCols'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api'
import SpecificationAttributeConfigs from '../specificationAttribute/SpecificationAttributeConfigs'

const useSpecificationAttributeGroupUpdateViewModel = () => {
    const [form] = Form.useForm()
    const navigate = useNavigate()
    const [selectedModalRowKeys, setSelectedModalRowKeys] = useState<React.Key[]>([])
    const { id } = useParams<{ id: string }>()
    const layout = {
        labelCol: { span: 8 },
        wrapperCol: { span: 16 },
    }
    const [modalData, setModalData] = useState<SpecificationAttributeResponse[]>()

    const onModalSelectChange = useCallback((newSelectedModalRowKeys: React.Key[]) => {
        setSelectedModalRowKeys(newSelectedModalRowKeys)
    }, [])

    const unGroupColumns = getSpecAttrUnGroupCols

    const { data } = useGetByIdApi<SpecificationAttributeGroupResponse>(
        SpecificationAttributeGroupConfigs.resourceUrl,
        SpecificationAttributeGroupConfigs.resourceKey,
        Number(id),
    )

    useEffect(() => {
        if (data) {
            form.setFieldsValue({
                name: data.name,
                displayOrder: data.displayOrder,
            })
            setModalData(data.specificationAttributes)
        }
    }, [data, form])

    const updateApi = useUpdateApi<SpecificationAttributeResponse>(
        SpecificationAttributeGroupConfigs.resourceUrl,
        SpecificationAttributeGroupConfigs.resourceKey,
        Number(id),
    )

    const deleteApi = useDeleteByIdApi(
        SpecificationAttributeGroupConfigs.resourceUrl,
        SpecificationAttributeGroupConfigs.resourceKey,
    )

    const handleDelete = () => {
        Modal.confirm({
            title: 'Are you sure you want to delete this item?',
            content: 'This action cannot be undone.',
            okText: 'Delete',
            okType: 'danger',
            onOk: () => {
                deleteApi.mutate(Number(id), {
                    onSuccess: () => {
                        navigate(ManagerPath.SPECIFICATION_ATTRIBUTE)
                    },
                })
            },
        })
    }

    const { mutate: deleteAtbApi } = useDeleteByIdsApi<number>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
    )

    const handleModalDelete = useCallback(() => {
        Modal.confirm({
            title: 'Are you sure you want to delete the selected attributes?',
            content: 'This action cannot be undone.',
            okText: 'Delete',
            okType: 'danger',
            onOk: async () => {
                deleteAtbApi(selectedModalRowKeys as number[], {
                    onSuccess: () => {
                        setSelectedModalRowKeys([])
                    },
                })
            },
        })
    }, [selectedModalRowKeys, deleteAtbApi])

    const onFinish = (values: SpecificationAttributeResponse) => {
        updateApi.mutate(values, {
            onSuccess: () => {
                navigate(ManagerPath.SPECIFICATION_ATTRIBUTE)
            },
        })
    }

    const handleUpdate = () => {
        form.submit()
    }

    return {
        form,
        handleDelete,
        onFinish,
        layout,
        handleUpdate,
        unGroupColumns,
        modalData,
        rowSelection: {
            selectedRowKeys: selectedModalRowKeys,
            onChange: onModalSelectChange,
        },
        handleModalDelete,
        selectedModalRowKeys,
    }
}

export default useSpecificationAttributeGroupUpdateViewModel
