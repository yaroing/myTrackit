import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/transfert">
        <Translate contentKey="global.menu.entities.transfert" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/type-action">
        <Translate contentKey="global.menu.entities.typeAction" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/staff">
        <Translate contentKey="global.menu.entities.staff" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/point-focal-point-service">
        <Translate contentKey="global.menu.entities.pointFocalPointService" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/suivi-mission">
        <Translate contentKey="global.menu.entities.suiviMission" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/action">
        <Translate contentKey="global.menu.entities.action" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/zrosts">
        <Translate contentKey="global.menu.entities.zrosts" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/stock-point-service">
        <Translate contentKey="global.menu.entities.stockPointService" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/point-service">
        <Translate contentKey="global.menu.entities.pointService" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/monitoring">
        <Translate contentKey="global.menu.entities.monitoring" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/requete-partenaire">
        <Translate contentKey="global.menu.entities.requetePartenaire" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/stock-partenaire">
        <Translate contentKey="global.menu.entities.stockPartenaire" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/mission">
        <Translate contentKey="global.menu.entities.mission" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/details-requete">
        <Translate contentKey="global.menu.entities.detailsRequete" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/partenaire">
        <Translate contentKey="global.menu.entities.partenaire" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/item-verifie">
        <Translate contentKey="global.menu.entities.itemVerifie" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/location">
        <Translate contentKey="global.menu.entities.location" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/requete-point-service">
        <Translate contentKey="global.menu.entities.requetePointService" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/section">
        <Translate contentKey="global.menu.entities.section" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/transporteur">
        <Translate contentKey="global.menu.entities.transporteur" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/catalogue">
        <Translate contentKey="global.menu.entities.catalogue" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/item-transfert">
        <Translate contentKey="global.menu.entities.itemTransfert" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/point-focal-partenaire">
        <Translate contentKey="global.menu.entities.pointFocalPartenaire" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
