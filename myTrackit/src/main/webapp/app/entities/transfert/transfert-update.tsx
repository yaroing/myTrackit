import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITransfert } from 'app/shared/model/transfert.model';
import { getEntity, updateEntity, createEntity, reset } from './transfert.reducer';

export const TransfertUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const transfertEntity = useAppSelector(state => state.mytrackit.transfert.entity);
  const loading = useAppSelector(state => state.mytrackit.transfert.loading);
  const updating = useAppSelector(state => state.mytrackit.transfert.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.transfert.updateSuccess);

  const handleClose = () => {
    navigate('/transfert');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateExp = convertDateTimeToServer(values.dateExp);
    values.dateRec = convertDateTimeToServer(values.dateRec);

    const entity = {
      ...transfertEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dateExp: displayDefaultDateTime(),
          dateRec: displayDefaultDateTime(),
        }
      : {
          ...transfertEntity,
          dateExp: convertDateTimeFromServer(transfertEntity.dateExp),
          dateRec: convertDateTimeFromServer(transfertEntity.dateRec),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.transfert.home.createOrEditLabel" data-cy="TransfertCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.transfert.home.createOrEditLabel">Create or edit a Transfert</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="transfert-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.transfert.dateExp')}
                id="transfert-dateExp"
                name="dateExp"
                data-cy="dateExp"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.transfert.nomChauffeur')}
                id="transfert-nomChauffeur"
                name="nomChauffeur"
                data-cy="nomChauffeur"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.transfert.dateRec')}
                id="transfert-dateRec"
                name="dateRec"
                data-cy="dateRec"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.transfert.cphone')}
                id="transfert-cphone"
                name="cphone"
                data-cy="cphone"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/transfert" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TransfertUpdate;
