/* 
 * Copyright (C) 2018 Nils Kuttkat <nkuttkat@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package abstractgame.unit;

/**
 * The Class MobileUnitRole.
 *
 * @author Nils Kuttkat
 */
public final class MobileUnitRole extends AbstractMobilityRole {

    /**
     * Gets the role.
     *
     * @return the abstract mobility role
     */
    public static AbstractMobilityRole GetRole() {
        if (AbstractMobilityRole.INSTANCE == null) {
            AbstractMobilityRole.INSTANCE = new MobileUnitRole();
        }

        return AbstractMobilityRole.INSTANCE;
    }

    ;

	/**
	 * Instantiates a new mobile unit role.
	 */
	private MobileUnitRole() {
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see abstractgame.unit.AbstractMobilityRole#isImmobile()
     */
    @Override
    public boolean isImmobile() {
        return false;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see abstractgame.unit.AbstractMobilityRole#isMobile()
     */
    @Override
    public boolean isMobile() {
        return true;
    }

}
