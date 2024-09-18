import { useState } from 'react'
import { Form } from 'antd'
import { CheckboxChangeEvent } from 'antd/es/checkbox'
import useCreateApi from '@/hooks/use-create-api'
import DiscountConfigs from './DiscountConfigs'
import { DiscountRequest } from '@/model/Discount'
import { useNavigate } from 'react-router-dom'
import ManagerPath from '@/constants/ManagerPath'
import { DiscountLimitationEnum } from './DiscountLimitationEnum'

function useDiscountCreateViewModel() {
    const [form] = Form.useForm()
    const [usePercentage, setUsePercentage] = useState<boolean>(false)
    const [requiresCouponCode, setRequiresCouponCode] = useState<boolean>(false)
    const [discountLimitation, setDiscountLimitation] = useState<string>('0')
    const [discountType, setDiscountType] = useState<string>('0')
    const navigate = useNavigate()

    const { mutate: createDiscount, isSuccess: discountLoading } = useCreateApi<DiscountRequest>(
        DiscountConfigs.resourceUrl,
    )

    const handleUsePercentageChange = (e: CheckboxChangeEvent) => {
        setUsePercentage(e.target.checked)
        // Clear discountPercentage and maxDiscountAmount if not using percentage
        if (!e.target.checked) {
            form.setFieldsValue({
                discountPercentage: null,
                maxDiscountAmount: null,
            })
        }
    }

    // Change handling function for "Requires coupon code" checkbox
    const handleRequiresCouponCodeChange = (e: CheckboxChangeEvent) => {
        setRequiresCouponCode(e.target.checked)
    }

    //Change handling function for Select "Discount limitation"
    const handleDiscountLimitationChange = (value: string) => {
        setDiscountLimitation(value)
    }

    // Change handling function for Select "Discount type"
    const handleDiscountTypeChange = (value: string) => {
        setDiscountType(value)
    }

    const handleSubmit = (values: any) => {
        const discountRequest: DiscountRequest = {
            isActive: values.isActive,
            name: values.name,
            discountTypeId: Number(values.discountType),
            appliedToSubCategories: values.appliedToSubCategories || false,
            usePercentage: values.usePercentage,
            discountPercentage: values.usePercentage ? values.discountPercentage : null,
            maxDiscountAmount: values.usePercentage ? values.maxDiscountAmount : null,
            requiresCouponCode: values.requiresCouponCode,
            couponCode: values.requiresCouponCode ? values.couponCode : null,
            discountLimitationId: Number(values.discountLimitationId),
            startDateUtc: values.startDate?.toISOString() || '',
            endDateUtc: values.endDate?.toISOString() || '',
            comment: values.comment || '',
            discountAmount: values.usePercentage ? null : values.discountAmount,
            isCumulative: values.isCumulative || false,
            limitationTimes: [
                DiscountLimitationEnum.N_TIMES_ONLY,
                DiscountLimitationEnum.N_TIMES_PER_CUSTOMER,
            ].includes(values.discountLimitationId)
                ? values.limitationTimes
                : DiscountLimitationEnum.UNLIMITED,
            maxDiscountedQuantity: values.maxDiscountedQuantity || null,
            minOderAmount: values.minOderAmount || null,
        }
        createDiscount(discountRequest, {
            onSuccess: () => {
                navigate(ManagerPath.DISCOUNT)
            },
        })
    }

    return {
        form,
        usePercentage,
        requiresCouponCode,
        discountLimitation,
        discountType,
        handleUsePercentageChange,
        handleRequiresCouponCodeChange,
        handleDiscountLimitationChange,
        handleDiscountTypeChange,
        handleSubmit,
        discountLoading,
    }
}

export default useDiscountCreateViewModel
