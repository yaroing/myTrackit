import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRequetePartenaire } from 'app/shared/model/requete-partenaire.model';
import { getEntity, updateEntity, createEntity, reset } from './requete-partenaire.reducer';

export const RequetePartenaireUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const requetePartenaireEntity = useAppSelector(state => state.mytrackit.requetePartenaire.entity);
  const loading = useAppSelector(state => state.mytrackit.requetePartenaire.loading);
  const updating = useAppSelector(state => state.mytrackit.requetePartenaire.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.requetePartenaire.updateSuccess);

  const handleClose = () => {
    navigate('/requete-partenaire');
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
    values.requeteDate = convertDateTimeToServer(values.requeteDate);

    const entity = {
      ...requetePartenaireEntity,
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
          requeteDate: displayDefaultDateTime(),
        }
      : {
          ...requetePartenaireEntity,
          requeteDate: convertDateTimeFromServer(requetePartenaireEntity.requeteDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.requetePartenaire.home.createOrEditLabel" data-cy="RequetePartenaireCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.requetePartenaire.home.createOrEditLabel">Create or edit a RequetePartenaire</Translate>
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
                  id="requete-partenaire-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.requetePartenaire.requeteDate')}
                id="requete-partenaire-requeteDate"
                name="requeteDate"
                data-cy="requeteDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedBlobField
                label={translate('myTrackitApp.requetePartenaire.fichierAtache')}
                id="requete-partenaire-fichierAtache"
                name="fichierAtache"
                data-cy="fichierAtache"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('myTrackitApp.requetePartenaire.requeteObs')}
                id="requete-partenaire-requeteObs"
                name="requeteObs"
                data-cy="requeteObs"
                type="textarea"
              />
              <ValidatedField
                label={translate('myTrackitApp.requetePartenaire.reqTraitee')}
                id="requete-partenaire-reqTraitee"
                name="reqTraitee"
                data-cy="reqTraitee"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/requete-partenaire" replace color="info">
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

export default RequetePartenaireUpdate;
