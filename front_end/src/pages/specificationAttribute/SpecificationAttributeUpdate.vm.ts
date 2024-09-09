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

    const processColor = (color: string | null): string | null => {
        if (color === null || color === undefined || color === '') {
            return null // Returns null if color is null, undefined, or an empty string
        }
        return color // Returns the color value unchanged if not null or an empty string
    }

    const formatColorHex = (hex: string) => {
        // Ensure the color is a valid 6-character hex code
        if (!/^#[0-9A-Fa-f]{6}$/.test(hex)) {
            return '#FFFFFF' // Default to white if the color is not valid
        }
        return hex.toUpperCase()
    }

    const handleColorCheckChange = (e: CheckboxChangeEvent) => {
        const isChecked = e.target.checked
        setIsColorPickerVisible(isChecked)

        // If the checkbox is not selected, set the color to null or default value
        if (!isChecked) {
            setColor('#FFFFFF') // Or another default value if needed
        }
    }

    const handleColorChange = (value: AggregationColor) => {
        const { r, g, b } = value.toRgb() // Assuming value has a `toRgb` method returning an object with r, g, b properties
        function rgbToHex(r: number, g: number, b: number): string {
            return (
                '#' +
                [r, g, b]
                    .map((x) => {
                        const hex = x.toString(16)
                        return hex.length === 1 ? '0' + hex : hex
                    })
                    .join('')
                    .toUpperCase()
            )
        }
        const hex = rgbToHex(r, g, b)
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
        let finalColor: string | null = null
        if (isColorPickerVisible) {
            finalColor = color === '#FFFFFF' ? '#FFFFFF' : color
        } else {
            finalColor = null
        }

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
            colorSquaresRgb: option.color === '#FFFFFF' ? '#FFFFFF' : option.color,
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

        // Show confirmation dialog
        Modal.confirm({
            title: 'Are you sure you want to save?',
            content: 'Your changes will be saved, and you will be redirected.',
            okText: 'Yes',
            cancelText: 'No',
            onOk: () => {
                updateAttribute(dataToSend, {
                    onSuccess: () => {
                        navigate(ManagerPath.SPECIFICATION_ATTRIBUTE)
                    },
                })
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
        if (processedColor !== null) {
            setColor(processedColor)
            setIsColorPickerVisible(true) //Checkbox is selected if colored
        } else {
            setColor('#FFFFFF') // Or default color value
            setIsColorPickerVisible(false) // Checkbox is not selected if there is no color
        }
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
    }
}

export default useSpecificationAttributeUpdateViewModel
