import { useEffect, useState } from 'react'
import { Form, Modal } from 'antd'
import { DiscountRequest } from '@/model/Discount'
import { CheckboxChangeEvent } from 'antd/es/checkbox'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import useUpdateApi from '@/hooks/use-update-api'
import DiscountConfigs from './DiscountConfigs'
import { useNavigate, useParams } from 'react-router-dom'
import dayjs from 'dayjs'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'

function useDiscountUpdateViewModel() {
    const [form] = Form.useForm()

    const [usePercentage, setUsePercentage] = useState<boolean>(false)
    const [requiresCouponCode, setRequiresCouponCode] = useState<boolean>(false)
    const [discountLimitation, setDiscountLimitation] = useState<number>(0)
    const [discountTypeId, setDiscountTypeId] = useState<number>(0)
    const navigate = useNavigate()

    const { id } = useParams<{ id: string }>()
    const { data, isSuccess } = useGetByIdApi<DiscountRequest>(
        DiscountConfigs.resourceUrl,
        DiscountConfigs.resourceKey,
        Number(id),
    )

    const { mutate: updateDiscount, isSuccess: load } = useUpdateApi<DiscountRequest>(
        DiscountConfigs.resourceUrl,
        DiscountConfigs.resourceKey,
        Number(id),
    )
    const { mutate: deleteDiscount } = useDeleteByIdApi<number>(
        DiscountConfigs.resourceUrl,
        DiscountConfigs.resourceKey,
    )

    useEffect(() => {
        if (isSuccess && data) {
            form.setFieldsValue({
                ...data,
                startDate: data.startDateUtc ? dayjs(data.startDateUtc) : null,
                endDate: data.endDateUtc ? dayjs(data.endDateUtc) : null,
                comment: data.comment || '',
            })
            setUsePercentage(data.usePercentage)
            setRequiresCouponCode(data.requiresCouponCode)
            setDiscountLimitation(Number(data.discountLimitationId))
            setDiscountTypeId(Number(data.discountTypeId))
        }
    }, [isSuccess, data, form])

    const handleSubmit = (values: any) => {
        const discountRequest: DiscountRequest = {
            isActive: values.isActive,
            name: values.name,
            discountTypeId: Number(discountTypeId),
            appliedToSubCategories: values.appliedToSubCategories || false,
            usePercentage: usePercentage,
            discountPercentage: usePercentage ? values.discountPercentage : null,
            maxDiscountAmount: usePercentage ? values.maxDiscountAmount : null,
            requiresCouponCode: requiresCouponCode,
            couponCode: requiresCouponCode ? values.couponCode : null,
            discountLimitationId: Number(discountLimitation),
            startDateUtc: values.startDate?.toISOString() || '',
            endDateUtc: values.endDate?.toISOString() || '',
            comment: values.comment || '',
            discountAmount: usePercentage ? null : values.discountAmount,
            isCumulative: values.isCumulative || false,
            limitationTimes: [1, 2].includes(discountLimitation) ? values.limitationTimes : 0,
            maxDiscountedQuantity: values.maxDiscountedQuantity || null,
            minOderAmount: values.minOrderAmount || null,
        }
        updateDiscount(discountRequest, {
            onSuccess: () => {
                navigate('/admin/discounts')
            },
        })
    }
    const handleDelete = () => {
        Modal.confirm({
            title: 'Are you sure you want to delete this item?',
            content: 'This action cannot be undone.',
            okText: 'Delete',
            okType: 'danger',
            cancelText: 'Cancel',
            onOk: () => {
                deleteDiscount(Number(id), {
                    onSuccess: () => {
                        navigate(`/admin/discounts`)
                    },
                })
            },
        })
    }

    const handleDiscountTypeChange = (value: number) => {
        setDiscountTypeId(value)
        form.setFieldsValue({ discountTypeId: value })
    }

    const handleUsePercentageChange = (e: CheckboxChangeEvent) => {
        setUsePercentage(e.target.checked)
    }

    const handleRequiresCouponCodeChange = (e: CheckboxChangeEvent) => {
        setRequiresCouponCode(e.target.checked)
    }

    const handleDiscountLimitationChange = (value: number) => {
        setDiscountLimitation(value)
        form.setFieldsValue({ discountLimitationId: value })
    }

    return {
        form,
        usePercentage,
        requiresCouponCode,
        discountLimitation,
        discountTypeId,
        handleSubmit,
        handleDiscountTypeChange,
        handleUsePercentageChange,
        handleRequiresCouponCodeChange,
        handleDiscountLimitationChange,
        load,
        handleDelete,
    }
}

export default useDiscountUpdateViewModel
