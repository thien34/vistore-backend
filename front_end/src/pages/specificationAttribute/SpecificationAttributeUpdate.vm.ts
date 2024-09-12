import { Form, Modal } from 'antd'
import { getSpecAttrCols } from './SpecAttrCols'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api'
import SpecificationAttributeConfigs from './SpecificationAttributeConfigs'
import { useState } from 'react'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute'
import { useNavigate, useParams } from 'react-router-dom'
import useGetApi from '@/hooks/use-get-api'
import { SpecificationAttributeGroupNameResponse } from '@/model/SpecificationAttributeGroup'
import SpecificationAttributeGroupConfigs from '../specificationAttributeGroup/SpecificationAttributeGroupConfigs'
import ManagerPath from '@/constants/ManagerPath'
import useUpdateApi from '@/hooks/use-update-api'
import { AggregationColor } from 'antd/es/color-picker/color'
import { CheckboxChangeEvent } from 'antd/es/checkbox'

interface Option {
    id: number
    specificationAttribute: number | null | undefined
    name: string
    displayOrder: number
    color: string | null
    associatedProducts: number
}

interface FormValues {
    name: string
    group: string
    displayOrder: number
}

const useSpecificationAttributeUpdateViewModel = () => {
    const [options, setOptions] = useState<Option[]>([])
    const [form] = Form.useForm()
    const [modalForm] = Form.useForm()
    const { id } = useParams()
    const navigate = useNavigate()
    const [editingOption, setEditingOption] = useState<Option | null>(null)
    const [isModalVisible, setIsModalVisible] = useState(false)
    const [color, setColor] = useState('')
    const [isColorPickerVisible, setIsColorPickerVisible] = useState(false)

    const layout = {
        labelCol: { span: 5 },
        wrapperCol: { span: 15 },
    }

    const processColor = (color: string | null): string | null => {
        return color?.trim() || null
    }

    const formatColorHex = (hex: string) => (/^#[0-9A-Fa-f]{6}$/.test(hex) ? hex.toUpperCase() : '#FFFFFF')

    const handleColorCheckChange = (e: CheckboxChangeEvent) => {
        const isChecked = e.target.checked
        setIsColorPickerVisible(isChecked)
        setColor(isChecked ? color : '#FFFFFF')
    }

    const handleColorChange = (value: AggregationColor) => {
        const { r, g, b } = value.toRgb()
        const hex = `#${[r, g, b]
            .map((x) => x.toString(16).padStart(2, '0'))
            .join('')
            .toUpperCase()}`
        setColor(hex)
    }

    const handleCancel = () => {
        setIsModalVisible(false)
        setIsColorPickerVisible(false)
        setColor('#FFFFFF')
        modalForm.resetFields()
        setEditingOption(null)
    }

    const { data: groupData } = useGetApi<SpecificationAttributeGroupNameResponse[]>(
        `${SpecificationAttributeGroupConfigs.resourceUrl}/list-name`,
        SpecificationAttributeGroupConfigs.resourceKey,
    )

    const { mutate: deleteOptions } = useDeleteByIdsApi<number>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
    )

    const { data } = useGetByIdApi<SpecificationAttributeResponse>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
        Number(id),
    )

    const { mutate: deleteAttribute } = useDeleteByIdsApi<number>(
        SpecificationAttributeConfigs.resourceUrl,
        'attributes',
    )

    const { mutate: updateAttribute } = useUpdateApi<SpecificationAttributeResponse>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
        Number(id),
    )

    const handleOk = () => {
        const newOption = modalForm.getFieldsValue() as {
            optionName: string
            optionDisplayOrder: number
        }

        // Determine the final color to be saved
        const finalColor = isColorPickerVisible ? color : null

        if (editingOption) {
            setOptions((prevOptions) =>
                prevOptions.map((option) =>
                    option.id === editingOption.id
                        ? {
                              ...option,
                              name: newOption.optionName || '',
                              displayOrder: Number(newOption.optionDisplayOrder) || 0,
                              color: finalColor,
                          }
                        : option,
                ),
            )
        } else {
            setOptions((prevOptions) => [
                ...prevOptions,
                {
                    id: prevOptions.length + 1,
                    specificationAttribute: Number(id),
                    name: newOption.optionName || '',
                    displayOrder: Number(newOption.optionDisplayOrder) || 0,
                    associatedProducts: 0,
                    color: finalColor,
                },
            ])
        }
        handleCancel()
    }

    const handleSave = () => {
        const formValues = form.getFieldsValue() as FormValues

        const updatedOptions = options.map((option) => ({
            id: Number(option.id),
            name: option.name,
            colorSquaresRgb: option.color || '#FFFFFF',
            displayOrder: option.displayOrder,
            productSpecificationAttributeMappings: [],
            specificationAttributeId: Number(id),
        }))

        const dataToSend = {
            id: Number(id),
            name: formValues.name,
            displayOrder: Number(formValues.displayOrder),
            specificationAttributeGroupId: formValues.group === 'none' ? null : Number(formValues.group),
            listOptions: updatedOptions,
        }

        updateAttribute(dataToSend, {
            onSuccess: () => {
                navigate(ManagerPath.SPECIFICATION_ATTRIBUTE)
            },
        })
    }

    const handleDeleteAttribute = () => {
        Modal.confirm({
            title: 'Are you sure you want to delete this attribute?',
            content: 'This action cannot be undone.',
            onOk() {
                deleteAttribute([Number(id)], {
                    onSuccess: () => {
                        navigate(ManagerPath.SPECIFICATION_ATTRIBUTE)
                    },
                })
            },
        })
    }

    const showEditModal = (option: Option) => {
        setEditingOption(option)
        setIsModalVisible(true)
        modalForm.setFieldsValue({
            optionName: option.name,
            optionDisplayOrder: option.displayOrder,
        })

        // Update the state of color and checkbox
        const processedColor = processColor(option.color)
        setColor(processedColor || '#FFFFFF')
        setIsColorPickerVisible(processedColor !== null)
    }

    const handleDeleteOption = (optionId: number) => {
        Modal.confirm({
            title: 'Are you sure you want to delete this option?',
            onOk() {
                deleteOptions([optionId], {
                    onSuccess: () => {
                        setOptions((prevOptions) => prevOptions.filter((option) => option.id !== optionId))
                    },
                })
            },
        })
    }

    const columns = getSpecAttrCols({ onEdit: showEditModal, onDelete: handleDeleteOption })

    return {
        columns,
        form,
        modalForm,
        data,
        groupData,
        handleDeleteAttribute,
        handleSave,
        formatColorHex,
        handleColorChange,
        handleColorCheckChange,
        handleCancel,
        handleOk,
        color,
        editingOption,
        isModalVisible,
        isColorPickerVisible,
        setIsModalVisible,
        options,
        setOptions,
        layout,
    }
}

export default useSpecificationAttributeUpdateViewModel
